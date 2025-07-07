package hyundai.web.admin.service;

import hyundai.web.admin.dto.MessagePayload;
import hyundai.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 연령대별 사용자 조회 후 카카오 메시지 큐에 페이로드를 전송하는 서비스
 */
@Service
@RequiredArgsConstructor
public class MessageDispatchService {

    private final UserRepository userRepository;
    private final MessageProducerService messageProducer;

    /**
     * ageFrom 이상 ageTo 이하인 사용자에게 메시지를 큐에 발송
     * @param ageFrom 시작 연령(포함)
     * @param ageTo   종료 연령(포함)
     * @param content 본문 (첫줄은 자동 삽입)
     */
    @Transactional(readOnly = true)
    public void dispatchKakaoMessageByAgeGroup(int ageFrom, int ageTo, String content) {
        List<MessagePayload.Kakao> payloads = userRepository
                .findByAgeBetweenAndIsDeletedFalse(ageFrom, ageTo)
                .stream()
                .map(user -> new MessagePayload.Kakao(
                        user.getPhone(),
                        user.getName(),
                        content
                ))
                .toList();

        messageProducer.sendKakaoMessage(payloads);
    }

    @Transactional(readOnly = true)
    public void dispatchSmsMessageByAgeGroup(int ageFrom, int ageTo, String content) {
        List<MessagePayload.Sms> payloads = userRepository
                .findByAgeBetweenAndIsDeletedFalse(ageFrom, ageTo)
                .stream()
                .map(user -> new MessagePayload.Sms(
                        user.getPhone(),
                        user.getName(),
                        content
                ))
                .toList();

        messageProducer.sendSmsMessage(payloads);
    }
}
