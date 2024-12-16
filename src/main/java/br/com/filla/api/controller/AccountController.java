package br.com.filla.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.filla.api.domain.account.Account;
import br.com.filla.api.domain.account.AccountDtoRead;
import br.com.filla.api.domain.account.AccountDtoUpdate;
import br.com.filla.api.domain.account.AccountRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("account")
public class AccountController {

  @Autowired
  private AccountRepository repository;

  @PostMapping
  @Transactional
  public ResponseEntity<AccountDtoRead> create(@RequestBody @Valid AccountDtoUpdate dto,
      UriComponentsBuilder uriBuilder) {
    
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));

    var entity = new Account(dto);
    repository.save(entity);

    var uri = uriBuilder.path("account/{id}").buildAndExpand(entity.getId()).toUri();
    return ResponseEntity.created(uri).body(new AccountDtoRead(entity));

  }

  @PutMapping
  @Transactional
  public ResponseEntity<AccountDtoRead> update(@RequestBody @Valid AccountDtoUpdate dto) {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));

    Account entity = (Account) repository.findByUsername(dto.getUsername());
    entity.update(dto);

    return ResponseEntity.ok(new AccountDtoRead(entity));
  }


}
