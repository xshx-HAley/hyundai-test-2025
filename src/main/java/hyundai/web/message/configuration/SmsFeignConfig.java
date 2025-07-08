package hyundai.web.message.configuration;


import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class SmsFeignConfig {

    @Bean
    public RequestInterceptor smsAuthInterceptor() {
        return template -> {
            String credentials = "autoever:5678";
            String encoded = Base64.getEncoder()
                    .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
            template.header("Authorization", "Basic " + encoded);
            template.header("Content-Type", "application/x-www-form-urlencoded");
        };
    }
}
