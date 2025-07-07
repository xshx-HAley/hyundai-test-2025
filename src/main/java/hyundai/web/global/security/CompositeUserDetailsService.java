package hyundai.web.global.security;

import hyundai.web.global.crypto.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class CompositeUserDetailsService  implements UserDetailsService {
    private final InMemoryUserDetailsManager inMemory;
    private final CustomUserDetailsService  jpaService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // 1) 인메모리 관리자 계정 우선
            return inMemory.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            // 2) 없으면 JPA 사용자로 시도 (여기서 isEnabled()에 deleted 플래그 검사됨)
            return jpaService.loadUserByUsername(username);
        }
    }


}
