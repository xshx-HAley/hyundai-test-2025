package hyundai.web.message.configuration;

import feign.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Configuration
@EnableFeignClients
@RequiredArgsConstructor
public class FeignConfiguration {
    private final Environment environment;
    private static final String ACTIVE_PROFILE_LOCAL = "[local]";
    public static final String APPLICATION_FORM_URLENCODED_UTF8_VALUE =
            MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8";

    /**
     * feign 로깅처리 레벨설정
     * @return
     */
    @Bean
    Logger.Level feignLogLevel(){
        return Logger.Level.BASIC;
    }
}
