package br.com.filla.api.domain.scheduling.validations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.filla.api.config.exception.SchedulingValidationException;
import br.com.filla.api.domain.scheduling.SchedulingDtoCreate;
import br.com.filla.api.domain.scheduling.SchedulingRepository;


@Component
public class SchedulingConflictValidator implements SchedulingValidator {

  @Autowired
  private SchedulingRepository schedulingRepository;

  @Override
  public void validate(SchedulingDtoCreate dto) {
    var hasSchedulingConflict = schedulingRepository
        .existsByProfessionalIdAndServiceDate(dto.getProfessionalId(), dto.getServiceDate());

    if (hasSchedulingConflict)
      throw new SchedulingValidationException(
          "The professional has an appointment for the same time.");
  }

}
