package pitoshnaya.impact.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pitoshnaya.impact.entity.User;

@Component
public class JwtService {
  private final String secretKey;

  private final Duration tokenTtl;

  JwtService(
      @Value("${jwt.secret}") String secret, @Value("${jwt.token.ttl}") long tokenTtlInHours) {
    secretKey = secret;
    tokenTtl = Duration.ofHours(tokenTtlInHours);
  }

  public String generateToken(User user) {
    return Jwts.builder()
        .subject(user.getUsername())
        .claim("tokenVersion", user.getTokenVersion())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + tokenTtl.toMillis()))
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
      Jwts.parser().verifyWith(getSigningKey()).build().parse(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isTokenVersionValid(String token, User user) {
    Integer tokenVersion =
        Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("tokenVersion", Integer.class);
    return tokenVersion != null && tokenVersion.equals(user.getTokenVersion());
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  private boolean isTokenExpired(String token) {
    Date expiration =
        Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
    return expiration.before(new Date());
  }
}
