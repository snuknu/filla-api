package br.com.filla.api.controller;

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
import br.com.filla.api.domain.professional.Professional;
import br.com.filla.api.domain.professional.ProfessionalDtoCreate;
import br.com.filla.api.domain.professional.ProfessionalDtoRead;
import br.com.filla.api.domain.professional.ProfessionalDtoReadShort;
import br.com.filla.api.domain.professional.ProfessionalDtoUpdate;
import br.com.filla.api.domain.professional.ProfessionalRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("professional")
public class ProfessionalController {

  @Autowired
  private ProfessionalRepository repository;

  @PostMapping
  @Transactional
  public ResponseEntity<ProfessionalDtoRead> create(@RequestBody @Valid ProfessionalDtoCreate dto,
      UriComponentsBuilder uriBuilder) {
    var entity = new Professional(dto);
    repository.save(entity);
    var uri = uriBuilder.path("/professional/{id}").buildAndExpand(entity.getId()).toUri();
    return ResponseEntity.created(uri).body(new ProfessionalDtoRead(entity));
  }

  @GetMapping
  public ResponseEntity<Page<ProfessionalDtoRead>> read(Pageable pageable) {
    var page = repository.findAll(pageable).map(ProfessionalDtoRead::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/short")
  public ResponseEntity<Page<ProfessionalDtoReadShort>> readShort(
      @PageableDefault(size = 6, page = 0, sort = {"id"}) Pageable pageable) {
    var page = repository.findAllByActiveTrue(pageable).map(ProfessionalDtoReadShort::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<ProfessionalDtoRead> read(@PathVariable Long id){
    var entity = repository.getReferenceById(id);
    return ResponseEntity.ok(new ProfessionalDtoRead(entity));
  }
  
  @PutMapping
  @Transactional
  public ResponseEntity<ProfessionalDtoRead> update(@RequestBody @Valid ProfessionalDtoUpdate dto) {
    var entity = repository.getReferenceById(dto.getId());
    entity.update(dto);
    return ResponseEntity.ok(new ProfessionalDtoRead(entity));
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
