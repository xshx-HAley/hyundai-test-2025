package hyundai.web.message.listener;
import feign.FeignException;
import feign.Request;
import feign.Response;
import hyundai.web.admin.dto.MessagePayload;
import hyundai.web.message.client.KakaoFeignClient;
import hyundai.web.message.client.SmsFeignClient;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MessageListenerTest {
    @Mock KakaoFeignClient kakaoClient;
    @Mock SmsFeignClient smsClient;

    RateLimiter kakaoLimiter;

    RateLimiter smsLimiter;

    @InjectMocks
    MessageListener listener;

     MessagePayload.Kakao payload;

    @BeforeEach
    void setUp() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(100)
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .timeoutDuration(Duration.ZERO)
                .build();
        kakaoLimiter = RateLimiter.of("testKakao", config);
        smsLimiter   = RateLimiter.of("testSms",   config);
        MockitoAnnotations.openMocks(this);

        listener = new MessageListener(
                kakaoClient, smsClient, kakaoLimiter, smsLimiter
        );
        payload = MessagePayload.Kakao.builder()
                .userName("홍길동")
                .phone("010-1234-5678")
                .message("테스트 본문")
                .build();
        // 예시 사용자
        payload = new MessagePayload.Kakao("홍길동", "010-1234-5678", "테스트 본문");
    }

    @Test
    @DisplayName("카카오 메세지가 실패하면 SMS를 시도한다.")
    void whenKakaoThrows500_thenSmsFallbackCalled() {
        // 1) kakaoClient.sendMessage(...) 가 500 에러 던지도록 설정
        Response feignResponse = Response.builder()
                .status(500)
                .reason("Internal Server Error")
                .request(Request.create(
                        Request.HttpMethod.POST,
                        "/kakaotalk-messages",
                        Map.of(), null, StandardCharsets.UTF_8
                ))
                .build();
        FeignException fe = FeignException.errorStatus("sendMessage", feignResponse);
        doThrow(fe).when(kakaoClient).sendMessage( Map.of(
                "phone",   payload.getPhone(),
                "message", payload.getMessage()
        ));


        // 2) listener.handle(...) 실행
        listener.handle(payload);

        // 3) SMS 폴백(sendSms) 이 한 번 호출됐는지 검증
        MessagePayload.Sms expectedSms = MessagePayload.Sms.builder()
                .userName("홍길동")
                .phone("010-1234-5678")
                .message("홍길동님, 안녕하세요. 현대 오토에버입니다.\n테스트 본문")
                .build();

        verify(smsClient, times(1))
                .sendSms(eq(expectedSms.getPhone()), eq(Map.of("message", expectedSms.getMessage())
        ));
        verify(kakaoClient, times(1)).sendMessage(Map.of(
                "phone",   expectedSms.getPhone(),
                "message", expectedSms.getMessage()
        ));

    }
    @Test
    @DisplayName("카카오톡 API 200 응답 시 SMS 폴백이 호출되지 않아야 한다")
    void whenKakaoReturns200_thenNoSmsFallback() {

        // 1) kakaoClient.sendMessage(...)가 200 OK 리턴하도록 설정
        when(kakaoClient.sendMessage(anyMap()))
                .thenReturn(ResponseEntity.ok().build());

        // 2) 리스너 실행
        listener.handle(payload);

        // 3) SMS 폴백(sendSms) 이 한 번 호출됐는지 검증
        MessagePayload.Sms expectedSms = MessagePayload.Sms.builder()
                .userName("홍길동")
                .phone("010-1234-5678")
                .message("홍길동님, 안녕하세요. 현대 오토에버입니다.\n테스트 본문")
                .build();
        // 3) 카카오만 한 번 호출, SMS는 전혀 호출되지 않아야 함
        verify(kakaoClient, times(1)).sendMessage(anyMap());
        verifyNoInteractions(smsClient);
    }
}
