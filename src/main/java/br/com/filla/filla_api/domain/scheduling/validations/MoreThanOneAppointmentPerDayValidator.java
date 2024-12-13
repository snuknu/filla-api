package br.com.filla.filla_api.domain.scheduling.validations;

import org.springframework.beans.factory.annotation.Autowired;
import br.com.filla.filla_api.config.exception.SchedulingValidationException;
import br.com.filla.filla_api.domain.scheduling.SchedulingDtoCreate;
import br.com.filla.filla_api.domain.scheduling.SchedulingRepository;
import br.com.filla.filla_api.domain.util.Validator;

public class MoreThanOneAppointmentPerDayValidator extends Validator<SchedulingDtoCreate> {

  private static final int DURATION_OF_APPOINTMENT = 1;
  private static final int FIRST_TIME = 7;
  private static final int LAST_TIME = 19 - DURATION_OF_APPOINTMENT;

  @Autowired
  private SchedulingRepository schedulingRepository;


  public MoreThanOneAppointmentPerDayValidator(SchedulingDtoCreate dto) {
    super(dto);
  }

  @Override
  public void validate() throws Exception {

    var firstTime = dto.getServiceDate().withHour(FIRST_TIME);
    var lastTime = dto.getServiceDate().withHour(LAST_TIME);

    var hasMorThanScheduling = schedulingRepository
        .existsByCustomerIdAndSchedulingDateBetween(dto.getProfessionalId(), firstTime, lastTime);

    if (hasMorThanScheduling)
      throw new SchedulingValidationException(
          "The customer already has an scheduling for the day.");
  }

}
