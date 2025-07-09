package hyundai.web.message.listener;

import feign.FeignException;
import hyundai.web.admin.dto.MessagePayload;
import hyundai.web.message.client.KakaoFeignClient;
import hyundai.web.message.client.SmsFeignClient;
import hyundai.web.message.configuration.RabbitConfiguration;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class MessageListener {

    private final KakaoFeignClient kakaoClient;
    private final SmsFeignClient smsClient;
    private final RateLimiter      kakaoLimiter;
    private final RateLimiter      smsLimiter;

    public MessageListener(
            KakaoFeignClient kakaoClient,
            SmsFeignClient   smsClient,
            @Qualifier("kakaoRateLimiter") RateLimiter kakaoLimiter,
            @Qualifier("smsRateLimiter")   RateLimiter smsLimiter
    ) {
        this.kakaoClient  = kakaoClient;
        this.smsClient    = smsClient;
        this.kakaoLimiter = kakaoLimiter;
        this.smsLimiter   = smsLimiter;
    }
    @RabbitListener(queues = RabbitConfiguration.KAKAO_QUEUE)
    public void handle(MessagePayload.Kakao payload) {
        log.info(">>> 메시지 수신 완료: {}", payload.getPhone());
        // 메시지 포맷팅: 첫줄 자동 삽입
        String fullMessage = payload.getUserName() +
                "님, 안녕하세요. 현대 오토에버입니다.\n" + payload.getMessage();

        Map<String,String> body = Map.of(
                "phone",   payload.getPhone(),
                "message", fullMessage
        );

        try {
            // Feign 호출을 RateLimiter로 감싸서 분당 100회 제한
            // 1) 카카오톡 호출 (RateLimiter 적용)
            ResponseEntity<Void> response = RateLimiter
                    .decorateCheckedSupplier(kakaoLimiter, () ->
                            kakaoClient.sendMessage(body)
                    ).get();

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.warn("카카오톡 API 응답 코드 {} → SMS 폴백으로 전환", response.getStatusCode());
                fallbackSms(payload, fullMessage);
            } else {
                log.info("카카오톡 발송 성공 (전화번호: {})", payload.getPhone());
            }
        } catch (RequestNotPermitted rl) {
            log.warn("카카오톡 Rate limit 초과 → SMS 폴백으로 전환");
            fallbackSms(payload, fullMessage);

        } catch (FeignException fe) {
            // Feign will throw for 4xx/5xx by default
            log.error("카카오톡 API 오류 (상태코드 {}) → SMS 폴백: {}", fe.status(), fe.getMessage());
            fallbackSms(payload, fullMessage);
        } catch (Exception ex) {
            System.err.println("카카오톡 전송 중 예기치 않은 오류 → SMS 폴백" + ex);
            fallbackSms(payload, fullMessage);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    private void fallbackSms(MessagePayload.Kakao payload, String message) {
        try {
            RateLimiter.decorateRunnable(smsLimiter,
                    () -> smsClient.sendSms(payload.getPhone(),
                            Map.of("message", message)
                    )
            ).run();
            log.info("SMS 폴백 발송 성공 (전화번호: {})", payload.getPhone());

        } catch (RequestNotPermitted e) {
            System.err.println("SMS Rate limit 초과");
        } catch (Exception ex) {
            System.err.println("SMS 전송 실패: " + ex.getMessage());
        }
    }
}
