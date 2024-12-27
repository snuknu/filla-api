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
import br.com.filla.api.domain.authorization.role.Role;
import br.com.filla.api.domain.authorization.role.RoleDtoRead;
import br.com.filla.api.domain.authorization.role.RoleDtoWrite;
import br.com.filla.api.domain.authorization.role.RoleRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("role")
@SecurityRequirement(name = "bearer-key")
public class RoleController {

  @Autowired
  private RoleRepository roleRepository;

  @PostMapping
  @Transactional
  public ResponseEntity<RoleDtoRead> create(@RequestBody @Valid RoleDtoWrite dto,
      UriComponentsBuilder uriBuilder) {

    var entity = new Role(dto);
    roleRepository.save(entity);

    var uri = uriBuilder.path("role/{id}").buildAndExpand(entity.getId()).toUri();
    return ResponseEntity.created(uri).body(new RoleDtoRead(entity));

  }

  @GetMapping
  @PageableAsQueryParam
  public ResponseEntity<Page<RoleDtoRead>> read(
      @Parameter(hidden = true) @PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pageable) {
    var page = roleRepository.findAll(pageable).map(RoleDtoRead::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<RoleDtoRead> readDetail(@PathVariable Long id) {
    var entity = roleRepository.getReferenceById(id);
    return ResponseEntity.ok(new RoleDtoRead(entity));
  }

  @PutMapping
  @Transactional
  public ResponseEntity<RoleDtoRead> update(@RequestBody @Valid RoleDtoWrite dto) throws Exception {

    if (!roleRepository.existsById(dto.getId()))
      throw new Exception("The role does not exist!");

    var entity = roleRepository.getReferenceById(dto.getId());
    entity.setName(dto.getName());

    return ResponseEntity.ok(new RoleDtoRead(entity));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<?> delete(@PathVariable Long id) {
    roleRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}
