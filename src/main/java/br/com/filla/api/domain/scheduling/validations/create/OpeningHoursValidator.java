package br.com.filla.api.domain.scheduling.validations.create;

import java.time.DayOfWeek;
import org.springframework.stereotype.Component;
import br.com.filla.api.config.exception.SchedulingValidationException;
import br.com.filla.api.domain.scheduling.SchedulingDtoCreate;

@Component
public class OpeningHoursValidator implements SchedulingCreateValidator {

  private static final int OPENING_TIME = 7;
  private static final int CLOSING_TIME = 19;
  private static final int DURATION_OF_SERVICE = 1;

  @Override
  public void validate(SchedulingDtoCreate dto) {

    var serviceDate = dto.getServiceDate();

    var isSunday = serviceDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    var isBeforeOpening = serviceDate.getHour() < OPENING_TIME;
    var isAfterOpening = serviceDate.getHour() > (CLOSING_TIME - DURATION_OF_SERVICE);

    if (isSunday || isBeforeOpening || isAfterOpening)
      throw new SchedulingValidationException("Scheduling outside of business hours.");
  }

}
