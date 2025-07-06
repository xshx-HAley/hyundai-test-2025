package hyundai.web.global.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;  // JWT를 서명할 때 사용할 비밀 키

    private final long expirationMs = 1000 * 60 * 60; // 1시간 유효 기간

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes); // HS256 알고리즘에 적합한 Key 객체 반환
    }
    //로그인 토큰 생성
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)                          // 토큰에 사용자 정보 (userId) 저장
                .setIssuedAt(new Date())                     // 발행 시각
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))  // 만료 시각
                .signWith(SignatureAlgorithm.HS256, secretKey) // HMAC-SHA256 서명
                .compact();                                  // JWT 문자열 생성
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // 예외가 없으면 유효
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}