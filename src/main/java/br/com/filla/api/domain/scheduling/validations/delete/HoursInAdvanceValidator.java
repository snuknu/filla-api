package br.com.filla.api.domain.scheduling.validations.delete;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import br.com.filla.api.config.exception.SchedulingValidationException;
import br.com.filla.api.domain.scheduling.Scheduling;

@Component("HoursInAdvanceDeleteValidator")
public class HoursInAdvanceValidator implements SchedulingDeleteValidator {

  
  private static final int HOURS_IN_ADVANCE_FOR_CANCELLATION = 24;

  @Override
  public void validate(Scheduling scheduling) {

    var serviceDate = scheduling.getServiceDate();
    var now = LocalDateTime.now();
    var differenceInHours = Duration.between(now, serviceDate).toHours();

    if (differenceInHours < HOURS_IN_ADVANCE_FOR_CANCELLATION)
      throw new SchedulingValidationException("Schedulings must be cancelled 24 hours in advance.");
  }
}
