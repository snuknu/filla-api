package br.com.filla.filla_api.controller;

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
import br.com.filla.filla_api.domain.employee.Employee;
import br.com.filla.filla_api.domain.employee.EmployeeDtoCreate;
import br.com.filla.filla_api.domain.employee.EmployeeDtoRead;
import br.com.filla.filla_api.domain.employee.EmployeeDtoReadShort;
import br.com.filla.filla_api.domain.employee.EmployeeDtoUpdate;
import br.com.filla.filla_api.domain.employee.EmployeeRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("employee")
public class EmployeeController {

  @Autowired
  private EmployeeRepository repository;

  @PostMapping
  @Transactional
  public ResponseEntity<EmployeeDtoRead> create(@RequestBody @Valid EmployeeDtoCreate dto,
      UriComponentsBuilder uriBuilder) {
    var entity = new Employee(dto);
    repository.save(entity);
    var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(entity.getId()).toUri();
    return ResponseEntity.created(uri).body(new EmployeeDtoRead(entity));
  }

  @GetMapping
  public ResponseEntity<Page<EmployeeDtoRead>> read(Pageable pageable) {
    var page = repository.findAll(pageable).map(EmployeeDtoRead::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/short")
  public ResponseEntity<Page<EmployeeDtoReadShort>> readShort(
      @PageableDefault(size = 6, page = 0, sort = {"id"}) Pageable pageable) {
    var page = repository.findAllByActiveTrue(pageable).map(EmployeeDtoReadShort::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<EmployeeDtoRead> read(@PathVariable Long id){
    var entity = repository.getReferenceById(id);
    return ResponseEntity.ok(new EmployeeDtoRead(entity));
  }
  
  @PutMapping
  @Transactional
  public ResponseEntity<EmployeeDtoRead> update(@RequestBody @Valid EmployeeDtoUpdate dto) {
    var entity = repository.getReferenceById(dto.getId());
    entity.update(dto);
    return ResponseEntity.ok(new EmployeeDtoRead(entity));
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
