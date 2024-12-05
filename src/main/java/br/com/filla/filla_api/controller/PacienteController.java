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
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import br.com.filla.filla_api.domain.paciente.Paciente;
import br.com.filla.filla_api.domain.paciente.PacienteDtoCreate;
import br.com.filla.filla_api.domain.paciente.PacienteDtoRead;
import br.com.filla.filla_api.domain.paciente.PacienteDtoReadShort;
import br.com.filla.filla_api.domain.paciente.PacienteDtoUpdate;
import br.com.filla.filla_api.domain.paciente.PacienteRepository;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

  @Autowired
  private PacienteRepository repository;

  @PostMapping
  @Transactional
  public ResponseEntity<PacienteDtoRead> create(@RequestBody @Valid PacienteDtoCreate dto,
      UriComponentsBuilder uriBuilder) {
    var entity = new Paciente(dto);
    repository.save(entity);
    var uri = uriBuilder.path("pacientes/{id}").buildAndExpand(entity.getId()).toUri();
    return ResponseEntity.created(uri).body(new PacienteDtoRead(entity));

  }

  @GetMapping
  public ResponseEntity<Page<PacienteDtoRead>> read(Pageable pageable) {
    var page = repository.findAll(pageable).map(PacienteDtoRead::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/short")
  public ResponseEntity<Page<PacienteDtoReadShort>> readShort(
      @PageableDefault(size = 6, page = 0, sort = {"id"}) Pageable pageable) {
    var page = repository.findAllByActiveTrue(pageable).map(PacienteDtoReadShort::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<PacienteDtoRead> readDetail(@PathVariable Long id) {
    var entity = repository.getReferenceById(id);
    return ResponseEntity.ok(new PacienteDtoRead(entity));
  }
  
  @PutMapping
  @Transactional
  public ResponseEntity<PacienteDtoRead> update(@RequestBody @Valid PacienteDtoUpdate dto) {
    var entity = repository.getReferenceById(dto.getId());
    entity.update(dto);
    return ResponseEntity.ok(new PacienteDtoRead(entity));
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
