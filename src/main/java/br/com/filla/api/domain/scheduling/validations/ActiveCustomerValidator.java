package br.com.filla.api.domain.scheduling.validations;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.filla.api.config.exception.SchedulingValidationException;
import br.com.filla.api.domain.customer.CustomerRepository;
import br.com.filla.api.domain.scheduling.SchedulingDtoCreate;

@Component
public class ActiveCustomerValidator implements SchedulingValidator {

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public void validate(SchedulingDtoCreate dto) {

    var customer = customerRepository.findByIdAndActiveTrue(dto.getCustomerId());

    if (Objects.isNull(customer))
      throw new SchedulingValidationException(
          "Schedulings cannot be made for inactive customers.");
  }

}
