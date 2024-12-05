package br.com.filla.filla_api.config.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import br.com.filla.filla_api.domain.account.Account;

@Service
public class TokenManagerService {

  private static final String ISSUER = "API Filla";

  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(Account account) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      return JWT.create()
          .withIssuer(ISSUER)
          .withSubject(account.getUsername())
          .withClaim("id", account.getId())
          .withExpiresAt(expirationDate())
          .sign(algorithm);

    } catch (JWTCreationException exception) {
      throw new RuntimeException("The token cannot be generated.", exception);
    }
  }

  public String getSubject(String token) {

    DecodedJWT decodedJWT;

    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      
      JWTVerifier verifier = JWT
          .require(algorithm)
          .withIssuer(ISSUER)
          .build();

      decodedJWT = verifier.verify(token);

    } catch (JWTVerificationException exception) {
      throw new RuntimeException("Invalid or expired JWT token.", exception);
    }

    return decodedJWT.getSubject();
  }

  private Instant expirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }

}
