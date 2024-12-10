package br.com.filla.filla_api.controller;

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
import br.com.filla.filla_api.config.exception.AppointmentValidationException;
import br.com.filla.filla_api.domain.appointment.Appointment;
import br.com.filla.filla_api.domain.appointment.AppointmentDtoCreate;
import br.com.filla.filla_api.domain.appointment.AppointmentDtoDelete;
import br.com.filla.filla_api.domain.appointment.AppointmentDtoRead;
import br.com.filla.filla_api.domain.appointment.AppointmentDtoUpdate;
import br.com.filla.filla_api.domain.appointment.AppointmentRepository;
import br.com.filla.filla_api.domain.appointment.AppointmentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("appointment")
public class AppointmentController {

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Autowired
  private AppointmentService appointmentService;

  @PostMapping
  @Transactional
  public ResponseEntity<AppointmentDtoRead> create(@RequestBody @Valid AppointmentDtoCreate dto,
      UriComponentsBuilder uriBuilder) throws Exception {

    Appointment appointment = appointmentService.schedule(dto);

    var uri = uriBuilder.path("/appointment/{id}").buildAndExpand(appointment.getId()).toUri();
    return ResponseEntity.created(uri).body(new AppointmentDtoRead(appointment));
  }

  @GetMapping
  public ResponseEntity<Page<AppointmentDtoRead>> read(Pageable pageable) {
    var page = appointmentRepository.findAll(pageable).map(AppointmentDtoRead::new);
    return ResponseEntity.ok(page);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<AppointmentDtoRead> read(@PathVariable Long id) {
    var entity = appointmentRepository.getReferenceById(id);
    return ResponseEntity.ok(new AppointmentDtoRead(entity));
  }

  @PutMapping
  @Transactional
  public ResponseEntity<AppointmentDtoRead> update(@RequestBody @Valid AppointmentDtoUpdate dto) {
    var entity = appointmentRepository.getReferenceById(dto.getId());
    entity.update(dto);
    return ResponseEntity.ok(new AppointmentDtoRead(entity));
  }


  @DeleteMapping
  @Transactional
  public ResponseEntity<?> delete(@RequestBody AppointmentDtoDelete dto)
      throws AppointmentValidationException {

    if (!appointmentRepository.existsById(dto.getAppointmentId())) {
      throw new AppointmentValidationException("The query ID appointment does not exist!");
    }

    var appointment = appointmentRepository.getReferenceById(dto.getAppointmentId());
    appointment.cancel(dto.getReasonCancellation());

    return ResponseEntity.noContent().build();
  }
}
