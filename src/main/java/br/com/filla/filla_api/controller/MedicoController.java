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
import br.com.filla.filla_api.domain.medico.Medico;
import br.com.filla.filla_api.domain.medico.MedicoDtoCreate;
import br.com.filla.filla_api.domain.medico.MedicoDtoRead;
import br.com.filla.filla_api.domain.medico.MedicoDtoReadShort;
import br.com.filla.filla_api.domain.medico.MedicoDtoUpdate;
import br.com.filla.filla_api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("medicos")
public class MedicoController {

  @Autowired
  private MedicoRepository repository;

  @PostMapping
  @Transactional
  public ResponseEntity<MedicoDtoRead> create(@RequestBody @Valid MedicoDtoCreate dto,
      UriComponentsBuilder uriBuilder) {
    var entity = new Medico(dto);
    repository.save(entity);
    var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(entity.getId()).toUri();
    return ResponseEntity.created(uri).body(new MedicoDtoRead(entity));
  }

  @GetMapping
  public ResponseEntity<Page<MedicoDtoRead>> read(Pageable pageable) {
    var page = repository.findAll(pageable).map(MedicoDtoRead::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/short")
  public ResponseEntity<Page<MedicoDtoReadShort>> readShort(
      @PageableDefault(size = 6, page = 0, sort = {"id"}) Pageable pageable) {
    var page = repository.findAllByActiveTrue(pageable).map(MedicoDtoReadShort::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<MedicoDtoRead> read(@PathVariable Long id){
    var entity = repository.getReferenceById(id);
    return ResponseEntity.ok(new MedicoDtoRead(entity));
  }
  
  @PutMapping
  @Transactional
  public ResponseEntity<MedicoDtoRead> update(@RequestBody @Valid MedicoDtoUpdate dto) {
    var entity = repository.getReferenceById(dto.getId());
    entity.update(dto);
    return ResponseEntity.ok(new MedicoDtoRead(entity));
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
