package hyundai.web.global.security;

import hyundai.web.user.domain.UserEntity;
import hyundai.web.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserIdAndIsDeleted(username, Boolean.FALSE)
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 없습니다."));
        return new CustomUserDetails(user);
    }
}

