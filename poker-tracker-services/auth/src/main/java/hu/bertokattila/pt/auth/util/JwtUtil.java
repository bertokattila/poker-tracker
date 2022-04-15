package hu.bertokattila.pt.auth.util;

import hu.bertokattila.pt.auth.AuthUser;
import io.jsonwebtoken.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

  public String generateToken(AuthUser userDetails){
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userDetails.getId());
    return createToken(claims, userDetails.getUsername());
  }
  private String createToken(Map<String, Object> claims, String subject){
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))
            .signWith(SignatureAlgorithm.HS256, "secret")
            .compact();
  }
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }
  private Claims extractAllClaims(String token){
    return Jwts.parser()
            .setSigningKey("secret")
            .parseClaimsJws(token)
            .getBody();
  }
  public String extractUsername(String token){
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token){
    return extractClaim(token, Claims::getExpiration);
  }


  private Boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }
  public Optional<AuthUser> validateToken(String token){
    try{
      Claims claims = extractAllClaims(token);

      return Optional.of(new AuthUser(claims.getSubject(), "", new ArrayList<>(),claims.get("userId", Integer.class), ""));
    }catch (final SignatureException | ExpiredJwtException e) {
      return Optional.empty();
    }
  }

}
