package hyundai.web.message.listener;

import hyundai.web.admin.dto.MessagePayload;
import hyundai.web.message.client.KakaoFeignClient;
import hyundai.web.message.client.SmsFeignClient;
import hyundai.web.message.configuration.RabbitConfiguration;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final KakaoFeignClient kakaoClient;
    private final SmsFeignClient smsClient;
    private final RateLimiter      kakaoLimiter;
    private final RateLimiter      smsLimiter;


    @RabbitListener(queues = RabbitConfiguration.KAKAO_QUEUE)
    public void handle(MessagePayload.Kakao payload) {
        // 메시지 포맷팅: 첫줄 자동 삽입
        String fullMessage = payload.getUserName() +
                "님, 안녕하세요. 현대 오토에버입니다.\n" + payload.getMessage();

        Map<String,String> body = Map.of(
                "phone",   payload.getPhone(),
                "message", fullMessage
        );

        try {
            // Feign 호출을 RateLimiter로 감싸서 분당 100회 제한
            RateLimiter.decorateRunnable(kakaoLimiter,
                    () -> kakaoClient.sendMessage(body)
            ).run();
        } catch (RequestNotPermitted e) {
            // Rate limit 초과 시 로그 또는 폴백
            System.err.println("카카오톡 Rate limit 초과");
        } catch (Exception e) {
            // 호출 실패 시 SMS 폴백 로직 TODO
            System.err.println("카카오톡 전송 실패: " + e.getMessage());
        }
    }
    private void fallbackSms(MessagePayload.Kakao payload, String message) {
        try {
            RateLimiter.decorateRunnable(smsLimiter,
                    () -> smsClient.sendSms(
                            payload.getPhone(),
                            Map.of("message", message)
                    )
            ).run();
        } catch (RequestNotPermitted e) {
            System.err.println("SMS Rate limit 초과");
        } catch (Exception ex) {
            System.err.println("SMS 전송 실패: " + ex.getMessage());
        }
    }
}
