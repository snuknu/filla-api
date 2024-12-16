package br.com.filla.api.domain.scheduling.validations;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import br.com.filla.api.config.exception.SchedulingValidationException;
import br.com.filla.api.domain.scheduling.SchedulingDtoCreate;

@Component
public class HoursInAdvanceValidator implements SchedulingValidator {

  private static final int SHORTEST_TIME_IN_ADVANCE = 30;

  @Override
  public void validate(SchedulingDtoCreate dto) {

    var serviceDate = dto.getServiceDate();
    var now = LocalDateTime.now();
    var differenceInMinutes = Duration.between(now, serviceDate).toMinutes();

    if (differenceInMinutes < SHORTEST_TIME_IN_ADVANCE)
      throw new SchedulingValidationException("Schedulings must be made 30 minutes in advance.");
  }

}
