package hyundai.web.user.service;

import hyundai.web.global.crypto.AesEncryptor;
import hyundai.web.global.crypto.PasswordEncryptor;
import hyundai.web.global.exception.BaseException;
import hyundai.web.global.exception.ExceptionCode;
import hyundai.web.user.domain.UserEntity;
import hyundai.web.user.dto.UserInfoResponse;
import hyundai.web.user.dto.UserSignUpRequest;
import hyundai.web.user.dto.UserSignUpResponse;
import hyundai.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;
    private final AesEncryptor aesEncryptor;

    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest request) {
        String encryptedSocialNumber = aesEncryptor.encrypt(request.socialNumber());
        if (userRepository.existsBySocialNumber(encryptedSocialNumber)) {
            throw new BaseException(ExceptionCode.IS_EXIST_SOCIAL_NUMBER);
        }

        if (userRepository.existsBySocialNumber(request.userId())) {
            throw new BaseException(ExceptionCode.IS_EXIST_USER_ID);
        }

        UserEntity user = UserEntity.builder()
                .userId(request.userId())
                .name(request.name())
                .password(passwordEncryptor.encode(request.password()))
                .socialNumber(aesEncryptor.encrypt(request.socialNumber()))
                .phone(request.phone())
                .address(request.toAddress())
                .build();


        UserEntity saved = userRepository.save(user);

        return new UserSignUpResponse(saved.getUserId(), saved.getName());

    }

    /**
     * 로그인한 사용자가 본인의 정보를 조회할 수 있는 기능
     * @param userId 로그인한 사용자 ID
     * @return
     */
    @Transactional(readOnly = true)
    public UserInfoResponse getMyInfo(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(ExceptionCode.USER_NOT_FOUND));

        return UserInfoResponse.of(user);
    }
}