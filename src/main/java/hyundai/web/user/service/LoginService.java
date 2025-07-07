package hyundai.web.user.service;

import hyundai.web.global.crypto.PasswordEncryptor;
import hyundai.web.global.exception.BaseException;
import hyundai.web.global.exception.ExceptionCode;
import hyundai.web.global.security.JwtTokenProvider;
import hyundai.web.user.domain.UserEntity;
import hyundai.web.user.dto.LoginRequest;
import hyundai.web.user.dto.LoginResponse;
import hyundai.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;
    private final JwtTokenProvider jwtTokenProvider;
    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByUserIdAndIsDeleted(request.userId(), Boolean.FALSE)
                .orElseThrow(() -> new BaseException(ExceptionCode.USER_NOT_FOUND));

        if (!passwordEncryptor.matches(request.password(), user.getPassword())) {
            throw new BaseException(ExceptionCode.PASSWORD_NOT_MATCHED);
        }

        String token = jwtTokenProvider.generateToken(user.getUserId());
        return new LoginResponse(user.getUserId(), token);
    }
}
