package br.com.filla.api.controller;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("account")
@SecurityRequirement(name = "bearer-key")
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


  
  @GetMapping
  @PageableAsQueryParam
  public ResponseEntity<Page<AccountDtoRead>> read(
      @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pageable) {
    var page = repository.findAll(pageable).map(AccountDtoRead::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<AccountDtoRead> readDetail(@PathVariable Long id) {
    var entity = repository.getReferenceById(id);
    return ResponseEntity.ok(new AccountDtoRead(entity));
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

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<?> delete(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/disable/{id}")
  @Transactional
  public ResponseEntity<?> disable(@PathVariable Long id) {
    var entity = repository.getReferenceById(id);
    entity.disable();
    return ResponseEntity.noContent().build();
  }
  


}
