package br.com.filla.filla_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.filla.filla_api.config.security.TokenJwtDtoRead;
import br.com.filla.filla_api.config.security.TokenManagerService;
import br.com.filla.filla_api.domain.account.Account;
import br.com.filla.filla_api.domain.account.AccountDtoUpdate;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager manager;

  @Autowired
  private TokenManagerService tokenService;

  @PostMapping
  public ResponseEntity<TokenJwtDtoRead> login(@RequestBody @Valid AccountDtoUpdate account) {

    var authenticationToken = new UsernamePasswordAuthenticationToken(
        account.getUsername(),
        account.getPassword());

    var authentication = manager.authenticate(authenticationToken);
    
    var tokenJWT = tokenService.generateToken((Account) authentication.getPrincipal());

    return ResponseEntity.ok(new TokenJwtDtoRead(tokenJWT));
  }
}
