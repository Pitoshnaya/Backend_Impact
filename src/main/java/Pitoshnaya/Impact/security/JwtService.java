package Pitoshnaya.Impact.security;

import Pitoshnaya.Impact.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
    @Value("${jwt.secret:my-very-secret-key-that-is-definitely-long-enough!}")
    private String secretKey;
    @Value("${jwt.expiration:3600000}")
    private long jwtExpirationMs;


    public String generateToken(User user) {
        return Jwts.builder()
            .subject(user.getUsername())
            .claim("tokenVersion", user.getTokenVersion())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // 15 минут
            .signWith(getSigningKey())
            .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public boolean isTokenValid(String token) {
        if (isTokenExpired(token)) {
            return false;
        }
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenVersionValid(String token, User user) {
        Integer tokenVersion = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("tokenVersion", Integer.class);
        return tokenVersion != null && tokenVersion.equals(user.getTokenVersion());
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
        return expiration.before(new Date());
    }

}
