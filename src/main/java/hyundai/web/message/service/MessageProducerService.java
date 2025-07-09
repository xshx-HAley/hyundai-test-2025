package hyundai.web.message.service;

import hyundai.web.admin.dto.MessagePayload;
import hyundai.web.message.configuration.RabbitConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerService {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 사용자 리스트와 메시지 본문을 받아 큐에 페이로드로 전송
     */
    public void sendKakaoMessage(List<MessagePayload.Kakao> payloads) {

        log.info(">>> 메시지 발행 시도: {}", payloads);
        payloads.forEach(payload -> rabbitTemplate.convertAndSend(
                RabbitConfiguration.MESSAGE_EXCHANGE,
                RabbitConfiguration.KAKAO_ROUTING_KEY,
                payload
        ));
        log.info(">>> 메시지 발행 완료: {}", payloads);
    }

    public void sendSmsMessage(List<MessagePayload.Sms> payloads) {
        payloads.forEach(payload -> rabbitTemplate.convertAndSend(
                RabbitConfiguration.MESSAGE_EXCHANGE,
                RabbitConfiguration.SMS_ROUTING_KEY,
                payload
        ));
    }
}
