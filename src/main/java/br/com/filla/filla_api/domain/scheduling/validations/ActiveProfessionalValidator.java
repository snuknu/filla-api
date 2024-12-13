package br.com.filla.filla_api.domain.scheduling.validations;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.filla.filla_api.config.exception.SchedulingValidationException;
import br.com.filla.filla_api.domain.customer.CustomerRepository;
import br.com.filla.filla_api.domain.scheduling.SchedulingDtoCreate;
import br.com.filla.filla_api.domain.util.Validator;

public class ActiveProfessionalValidator extends Validator<SchedulingDtoCreate> {

  @Autowired
  private CustomerRepository professionalRepository;

  public ActiveProfessionalValidator(SchedulingDtoCreate dto) {
    super(dto);
  }

  @Override
  protected void validate() throws Exception {

    var hasSelectedProfessional = dto.getProfessionalId() == null;
    if (hasSelectedProfessional)
      return;

    var professional = professionalRepository.findByIdAndActiveTrue(dto.getProfessionalId());

    if (Objects.isNull(professional))
      throw new SchedulingValidationException(
          "Schedulings cannot be made for inactive professionals.");
  }

}
