package br.com.filla.filla_api.domain.scheduling.validations;

import org.springframework.beans.factory.annotation.Autowired;
import br.com.filla.filla_api.config.exception.SchedulingValidationException;
import br.com.filla.filla_api.domain.scheduling.SchedulingDtoCreate;
import br.com.filla.filla_api.domain.scheduling.SchedulingRepository;
import br.com.filla.filla_api.domain.util.Validator;

public class SchedulingConflictValidator extends Validator<SchedulingDtoCreate> {

  @Autowired
  private SchedulingRepository schedulingRepository;

  public SchedulingConflictValidator(SchedulingDtoCreate dto) {
    super(dto);
  }

  @Override
  protected void validate() throws Exception {

    var hasSchedulingConflict = schedulingRepository
        .existsByProfessionalIdAndServiceDate(dto.getProfessionalId(), dto.getServiceDate());

    if (hasSchedulingConflict)
      throw new SchedulingValidationException(
          "the professional already has another appointment scheduled for the same time");
  }

}
