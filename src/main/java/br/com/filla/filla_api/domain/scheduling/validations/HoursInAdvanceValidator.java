package br.com.filla.filla_api.domain.scheduling.validations;

import java.time.Duration;
import java.time.LocalDateTime;
import br.com.filla.filla_api.config.exception.SchedulingValidationException;
import br.com.filla.filla_api.domain.scheduling.SchedulingDtoCreate;
import br.com.filla.filla_api.domain.util.Validator;

public class HoursInAdvanceValidator extends Validator<SchedulingDtoCreate> {

  private static final int SHORTEST_TIME_IN_ADVANCE = 30;

  public HoursInAdvanceValidator(SchedulingDtoCreate dto) {
    super(dto);
  }

  @Override
  public void validate() throws Exception {

    var serviceDate = dto.getServiceDate();
    var now = LocalDateTime.now();
    var differenceInMinutes = Duration.between(serviceDate, now).toMinutes();

    if (differenceInMinutes < SHORTEST_TIME_IN_ADVANCE)
      throw new SchedulingValidationException("Schedulings must be made 30 minutes in advance.");
  }

}
