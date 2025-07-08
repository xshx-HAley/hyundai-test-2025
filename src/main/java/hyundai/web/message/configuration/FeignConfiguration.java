package hyundai.web.message.configuration;

import feign.Client;
import feign.Logger;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Configuration
@EnableFeignClients
@RequiredArgsConstructor
public class FeignConfiguration {
    private final Environment environment;
    private static final String ACTIVE_PROFILE_LOCAL = "[local]";
    public static final String APPLICATION_FORM_URLENCODED_UTF8_VALUE =
            MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8";

    /**
     * content-type 헤더를 UTF-8로 명시하기 위한 설정
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_FORM_URLENCODED_UTF8_VALUE);
    }

    /**
     * feign 로깅처리 레벨설정
     * @return
     */
    @Bean
    Logger.Level feignLogLevel(){
        return Logger.Level.BASIC;
    }

    /**
     * 임시 테스트용 - 개발/로컬에서만 SSL 인증서 없이 동작 하도록 SSL 인증서 검증을 우회하여, 모든 HTTPS 연결을 신뢰하도록 설정하는 SSLSocketFactory를 생성
     *
     * @return
     * @throws Exception
     */
    @Bean
    public SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }}, new SecureRandom());

            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    /**
//     * 로컬 서버의 SSL Disable 설정
//     * @return
//     */
//    @Bean
//    public Client getFeignClientDefaultByActiveProfile() {
//        // todo: parkhoc 요기 바꿔야함
//        return new Client.Default(getSSLSocketFactory(), new NoopHostnameVerifier());
////        if(StringUtil.equals(Arrays.toString(environment.getActiveProfiles()), ACTIVE_PROFILE_LOCAL)) {
////            //Feign SSL Disable
////            return new Client.Default(getSSLSocketFactory(), new NoopHostnameVerifier());
////        } else {
////            return new Client.Default(null, null);
////        }
//    }
}
