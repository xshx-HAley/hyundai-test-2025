package hyundai.web.admin.service;

import hyundai.web.admin.dto.MessagePayload;
import hyundai.web.message.configuration.RabbitConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageProducerService {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 사용자 리스트와 메시지 본문을 받아 큐에 페이로드로 전송
     */
    public void sendKakaoMessage(List<MessagePayload.Kakao> payloads) {
        payloads.forEach(payload -> rabbitTemplate.convertAndSend(
                RabbitConfiguration.MESSAGE_EXCHANGE,
                RabbitConfiguration.KAKAO_ROUTING_KEY,
                payload
        ));
    }

    public void sendSmsMessage(List<MessagePayload.Sms> payloads) {
        payloads.forEach(payload -> rabbitTemplate.convertAndSend(
                RabbitConfiguration.MESSAGE_EXCHANGE,
                RabbitConfiguration.SMS_ROUTING_KEY,
                payload
        ));
    }
}
