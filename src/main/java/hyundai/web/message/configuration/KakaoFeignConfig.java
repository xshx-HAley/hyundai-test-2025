package hyundai.web.message.configuration;

import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class KakaoFeignConfig {
    @Bean
    public RequestInterceptor kakaoAuthInterceptor() {
        return template -> {
            String credentials = "autoever:1234";
            String encoded = Base64.getEncoder()
                    .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
            template.header("Authorization", "Basic " + encoded);
            template.header("Content-Type", "application/json");
        };
    }

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(
                new SpringDecoder(() -> new HttpMessageConverters(
                        new MappingJackson2HttpMessageConverter()
                ))
        );
    }
    // 2) 에러 디코더: 무조건 null 리턴 → 예외 없이 status코드만 리턴
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> null;
    }
}

