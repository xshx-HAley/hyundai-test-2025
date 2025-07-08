package hyundai.web.message.client;

import hyundai.web.message.configuration.KakaoFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "kakaoClient",
        url  = "http://localhost:8081",
        configuration = KakaoFeignConfig.class
)
public interface KakaoFeignClient {
    @PostMapping(value = "/kakaotalk-messages", consumes = "application/json")
    void sendMessage(@RequestBody Map<String,String> payload);
}
