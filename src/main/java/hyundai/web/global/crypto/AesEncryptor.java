package hyundai.web.global.crypto;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AesEncryptor {
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";

    @Value("${crypto.aes.key}")
    private String key; // 32자리 (256bit)

    @Value("${crypto.aes.iv}")
    private String iv;  // 16자리 (128bit)

    private SecretKeySpec keySpec;
    private IvParameterSpec ivSpec;

    @PostConstruct
    public void init() {
        keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
    }

    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("AES 암호화 실패", e);
        }
    }

    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES 복호화 실패", e);
        }
    }
}
