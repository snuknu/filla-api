package br.com.filla.api.domain.scheduling.validations.create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.filla.api.config.exception.SchedulingValidationException;
import br.com.filla.api.domain.scheduling.SchedulingDtoCreate;
import br.com.filla.api.domain.scheduling.SchedulingRepository;

@Component
public class MoreThanOneSchedulingPerDayValidator implements SchedulingCreateValidator {

  private static final int DURATION_OF_APPOINTMENT = 1;
  private static final int FIRST_TIME = 7;
  private static final int LAST_TIME = 19 - DURATION_OF_APPOINTMENT;

  @Autowired
  private SchedulingRepository schedulingRepository;

  @Override
  public void validate(SchedulingDtoCreate dto) {

    var firstTime = dto.getServiceDate().withHour(FIRST_TIME);
    var lastTime = dto.getServiceDate().withHour(LAST_TIME);

    var hasMorThanScheduling = schedulingRepository
        .existsByCustomerIdAndServiceDateBetween(dto.getCustomerId(), firstTime, lastTime);

    if (hasMorThanScheduling)
      throw new SchedulingValidationException(
          "The customer already has an scheduling for the day.");
  }

}
