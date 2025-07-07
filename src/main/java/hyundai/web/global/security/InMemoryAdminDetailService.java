package hyundai.web.global.security;

import hyundai.web.global.crypto.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InMemoryAdminDetailService {

    private final PasswordEncryptor passwordEncryptor;
    /** 관리자 계정 임시 설정 **/
    @Bean
    public InMemoryUserDetailsManager adminDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncryptor.encode("1212"))
                        .disabled(Boolean.FALSE)
                        .roles("ADMIN")
                        .build()
        );
    }
}
