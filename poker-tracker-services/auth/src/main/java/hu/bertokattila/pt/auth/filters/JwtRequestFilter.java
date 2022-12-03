package hu.bertokattila.pt.auth.filters;

import hu.bertokattila.pt.auth.AuthUser;
import hu.bertokattila.pt.auth.util.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                  FilterChain filterChain) throws ServletException, IOException {
    (httpServletResponse).addHeader("Access-Control-Allow-Origin", "*");
    (httpServletResponse).addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
    (httpServletResponse).addHeader("Access-Control-Allow-Headers", "*");
    if (httpServletRequest.getMethod().equals("OPTIONS")) {
      httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
      return;
    }
    String authorizationHeader = httpServletRequest.getHeader("Authorization");
    String email = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      email = jwtUtil.extractUsername(jwt);
    }
    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      AuthUser user = jwtUtil.validateToken(jwt).orElse(null);
      if (user != null) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());

        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
