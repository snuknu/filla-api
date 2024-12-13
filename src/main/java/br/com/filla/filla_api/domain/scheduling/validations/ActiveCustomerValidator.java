package br.com.filla.filla_api.domain.scheduling.validations;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.filla.filla_api.config.exception.SchedulingValidationException;
import br.com.filla.filla_api.domain.customer.CustomerRepository;
import br.com.filla.filla_api.domain.scheduling.SchedulingDtoCreate;
import br.com.filla.filla_api.domain.util.Validator;

public class ActiveCustomerValidator extends Validator<SchedulingDtoCreate> {

  @Autowired
  private CustomerRepository customerRepository;

  public ActiveCustomerValidator(SchedulingDtoCreate dto) {
    super(dto);
  }

  @Override
  protected void validate() throws Exception {

    var customer = customerRepository.findByIdAndActiveTrue(dto.getCustomerId());

    if (Objects.isNull(customer))
      throw new SchedulingValidationException(
          "Schedulings cannot be made for inactive customers.");
  }

}
