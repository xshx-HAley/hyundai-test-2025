package hyundai.web.global.crypto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncryptor {

    private final PasswordEncoder passwordEncoder;

    public String encode(String raw) {
        return passwordEncoder.encode(raw);
    }

    public boolean matches(String raw, String encoded) {
        return passwordEncoder.matches(raw, encoded);
    }
}
