package br.com.filla.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import br.com.filla.api.domain.scheduling.Scheduling;
import br.com.filla.api.domain.scheduling.SchedulingDtoCreate;
import br.com.filla.api.domain.scheduling.SchedulingDtoDelete;
import br.com.filla.api.domain.scheduling.SchedulingDtoRead;
import br.com.filla.api.domain.scheduling.SchedulingDtoReadShort;
import br.com.filla.api.domain.scheduling.SchedulingDtoUpdate;
import br.com.filla.api.domain.scheduling.SchedulingRepository;
import br.com.filla.api.domain.scheduling.SchedulingService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("scheduling")
public class SchedulingController {

  @Autowired
  private SchedulingRepository schedulingRepository;

  @Autowired
  private SchedulingService schedulingService;

  @PostMapping
  @Transactional
  public ResponseEntity<SchedulingDtoReadShort> create(@RequestBody @Valid SchedulingDtoCreate dto,
      UriComponentsBuilder uriBuilder) throws Exception {

    Scheduling scheduling = schedulingService.schedule(dto);

    var uri = uriBuilder.path("/scheduling/{id}").buildAndExpand(scheduling.getId()).toUri();
    return ResponseEntity.created(uri).body(new SchedulingDtoReadShort(scheduling));
  }

  @GetMapping
  public ResponseEntity<Page<SchedulingDtoRead>> read(Pageable pageable) {
    var page = schedulingRepository.findAll(pageable).map(SchedulingDtoRead::new);
    return ResponseEntity.ok(page);
  }
  
  @GetMapping("short")
  public ResponseEntity<Page<SchedulingDtoReadShort>> readShort(Pageable pageable) {
    var page = schedulingRepository.findAll(pageable).map(SchedulingDtoReadShort::new);
    return ResponseEntity.ok(page);
  }


  @GetMapping(path = "/{id}")
  public ResponseEntity<SchedulingDtoRead> read(@PathVariable Long id) {
    var entity = schedulingRepository.getReferenceById(id);
    return ResponseEntity.ok(new SchedulingDtoRead(entity));
  }

  @PutMapping
  @Transactional
  public ResponseEntity<SchedulingDtoRead> update(@RequestBody @Valid SchedulingDtoUpdate dto)
      throws Exception {

    if (!schedulingRepository.existsById(dto.getId()))
      return ResponseEntity.notFound().build();

    var entity = schedulingService.reschedule(dto);

    return ResponseEntity.ok(new SchedulingDtoRead(entity));
  }


  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<?> delete(@PathVariable Long id,
      @RequestBody @Valid SchedulingDtoDelete dto) {

    if (!schedulingRepository.existsById(id))
      return ResponseEntity.notFound().build();

    var scheduling = schedulingRepository.getReferenceById(id);
    scheduling.cancel(dto.getReasonCancellation());

    return ResponseEntity.noContent().build();
  }
}
