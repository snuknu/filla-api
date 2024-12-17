package br.com.filla.api.domain.scheduling.validations.create;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.filla.api.config.exception.SchedulingValidationException;
import br.com.filla.api.domain.customer.CustomerRepository;
import br.com.filla.api.domain.scheduling.SchedulingDtoCreate;

@Component
public class ActiveProfessionalValidator implements SchedulingCreateValidator {

  @Autowired
  private CustomerRepository professionalRepository;

  @Override
  public void validate(SchedulingDtoCreate dto) {

    var hasSelectedProfessional = dto.getProfessionalId() == null;
    if (hasSelectedProfessional)
      return;

    var professional = professionalRepository.findByIdAndActiveTrue(dto.getProfessionalId());

    if (Objects.isNull(professional))
      throw new SchedulingValidationException(
          "Schedulings cannot be made for inactive professionals.");
  }

}
