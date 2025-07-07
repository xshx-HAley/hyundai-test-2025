package hyundai.web.message.configuration;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * 메시지 전송을 위한 RateLimiter 설정
 */

@Configuration
public class MessageRateLimiterConfiguration {
    /**
     * 카카오톡 메시지 API: 토큰 당 분당 100회 제한
     */
    @Bean
    public RateLimiter kakaoRateLimiter() {
        return RateLimiter.of("kakao",
                RateLimiterConfig.custom()
                        .limitForPeriod(100)
                        .limitRefreshPeriod(Duration.ofMinutes(1))
                        .timeoutDuration(Duration.ofSeconds(5))
                        .build()
        );
    }

    /**
     * SMS 메시지 API: 분당 500회 제한
     */
    @Bean
    public RateLimiter smsRateLimiter() {
        return RateLimiter.of("sms",
                RateLimiterConfig.custom()
                        .limitForPeriod(500)
                        .limitRefreshPeriod(Duration.ofMinutes(1))
                        .timeoutDuration(Duration.ofSeconds(5))
                        .build()
        );
    }

}
