package hyundai.web.message.client;

import hyundai.web.message.configuration.SmsFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "smsClient",
        url  = "http://localhost:8082",
        configuration = SmsFeignConfig.class
)
public interface SmsFeignClient {

    @PostMapping(value = "/sms", consumes = "application/x-www-form-urlencoded")
    void sendSms(@RequestParam("phone") String phone,
                 @RequestBody Map<String,String> form);
}


