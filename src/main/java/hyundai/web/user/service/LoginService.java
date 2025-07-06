package hyundai.web.user.service;

import hyundai.web.global.crypto.PasswordEncryptor;
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
        UserEntity user = userRepository.findByUserId(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncryptor.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getUserId());
        return new LoginResponse(user.getUserId(), token);
    }
}
