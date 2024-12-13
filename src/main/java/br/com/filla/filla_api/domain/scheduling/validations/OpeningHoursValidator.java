package br.com.filla.filla_api.domain.scheduling.validations;

import java.time.DayOfWeek;
import br.com.filla.filla_api.config.exception.SchedulingValidationException;
import br.com.filla.filla_api.domain.scheduling.SchedulingDtoCreate;
import br.com.filla.filla_api.domain.util.Validator;

public class OpeningHoursValidator extends Validator<SchedulingDtoCreate> {

  private static final int OPENING_TIME = 7;
  private static final int CLOSING_TIME = 19;
  private static final int DURATION_OF_APPOINTMENT = 1;

  public OpeningHoursValidator(SchedulingDtoCreate dto) {
    super(dto);
  }

  @Override
  public void validate() throws Exception {

    var serviceDate = dto.getServiceDate();

    var isSunday = serviceDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    var isBeforeOpening = serviceDate.getHour() < OPENING_TIME;
    var isAfterOpening = serviceDate.getHour() > (CLOSING_TIME - DURATION_OF_APPOINTMENT);

    if (isSunday || isBeforeOpening || isAfterOpening)
      throw new SchedulingValidationException("Scheduling outside of business hours.");
  }

}
