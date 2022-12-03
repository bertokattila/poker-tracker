package hu.bertokattila.pt.auth.util;

import hu.bertokattila.pt.auth.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  public String generateToken(AuthUser userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userDetails.getId());
    claims.put("defaultCurrency", userDetails.getDefaultCurrency());
    return createToken(claims, userDetails.getUsername());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))
            .signWith(SignatureAlgorithm.HS256, "secret")
            .compact();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
            .setSigningKey("secret")
            .parseClaimsJws(token)
            .getBody();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }


  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Optional<AuthUser> validateToken(String token) {
    try {
      Claims claims = extractAllClaims(token);

      return Optional.of(new AuthUser(claims.getSubject(), "", new ArrayList<>(),
              claims.get("userId", Integer.class), "", claims.get("defaultCurrency", String.class)));
    } catch (final SignatureException | ExpiredJwtException e) {
      return Optional.empty();
    }
  }

}
