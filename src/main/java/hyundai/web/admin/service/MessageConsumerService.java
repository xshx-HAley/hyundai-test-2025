package hyundai.web.admin.service;

import hyundai.web.admin.dto.MessagePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageConsumerService {


    @RabbitListener(queues = "hyundai.message.kakao")
    public void handle(MessagePayload.Kakao payload) {
        // 1) 메시지 포맷팅
        String fullMessage = payload.getUserName() + "님, 안녕하세요. 현대 오토에버입니다.\n" + payload.getMessage();

        // 2) RateLimiter 적용 & 3) RestTemplate 호출 => FEIGN으로 변경
//        RateLimiter.decorateCheckedSupplier(kakaoLimiter, () ->
//                restTemplate.postForEntity("http://localhost:8081/kakaotalk-messages", request, Void.class)
//        ).apply();

        // 4) 실패 시 SMS 폴백 등...
    }
}
