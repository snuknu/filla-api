package br.com.filla.api.controller;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.filla.api.domain.customer.Customer;
import br.com.filla.api.domain.customer.CustomerDtoCreate;
import br.com.filla.api.domain.customer.CustomerDtoRead;
import br.com.filla.api.domain.customer.CustomerDtoReadShort;
import br.com.filla.api.domain.customer.CustomerDtoUpdate;
import br.com.filla.api.domain.customer.CustomerRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("customer")
@SecurityRequirement(name = "bearer-key")
public class CustomerController {

  @Autowired
  private CustomerRepository repository;

  @PostMapping
  @Transactional
  public ResponseEntity<CustomerDtoRead> create(@RequestBody @Valid CustomerDtoCreate dto,
      UriComponentsBuilder uriBuilder) {
    var entity = new Customer(dto);
    repository.save(entity);
    var uri = uriBuilder.path("customer/{id}").buildAndExpand(entity.getId()).toUri();
    return ResponseEntity.created(uri).body(new CustomerDtoRead(entity));

  }

  @GetMapping
  @PageableAsQueryParam
  public ResponseEntity<Page<CustomerDtoRead>> read(
      @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pageable) {
    var page = repository.findAll(pageable).map(CustomerDtoRead::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/short")
  @PageableAsQueryParam
  public ResponseEntity<Page<CustomerDtoReadShort>> readShort(
      @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pageable) {
    var page = repository.findAllByActiveTrue(pageable).map(CustomerDtoReadShort::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<CustomerDtoRead> readDetail(@PathVariable Long id) {
    var entity = repository.getReferenceById(id);
    return ResponseEntity.ok(new CustomerDtoRead(entity));
  }
  
  @PutMapping
  @Transactional
  public ResponseEntity<CustomerDtoRead> update(@RequestBody @Valid CustomerDtoUpdate dto) {
    var entity = repository.getReferenceById(dto.getId());
    entity.update(dto);
    return ResponseEntity.ok(new CustomerDtoRead(entity));
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
