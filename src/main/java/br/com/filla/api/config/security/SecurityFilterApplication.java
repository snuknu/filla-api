package br.com.filla.api.config.security;

import java.io.IOException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import br.com.filla.api.domain.account.AccountRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilterApplication extends OncePerRequestFilter {

  private static final String HEADER_AUTHORIZATION = "Authorization";
  private static final String HEADER_AUTHORIZATION_PREFIX = "Bearer ";

  @Autowired
  private TokenManagerService tokenService;

  @Autowired
  private AccountRepository repository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    var tokenJWT = extractToken(request);

    if (Objects.nonNull(tokenJWT)) {

      var subject = tokenService.getSubject(tokenJWT);
      var account = repository.findByUsername(subject);
      var authentication =
          new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    filterChain.doFilter(request, response);

  }

  private String extractToken(HttpServletRequest request) {

    var headerAuthorization = request.getHeader(HEADER_AUTHORIZATION);

    if (Objects.nonNull(headerAuthorization)) {
      return headerAuthorization.replace(HEADER_AUTHORIZATION_PREFIX, "");
    }

    return null;
  }

}
