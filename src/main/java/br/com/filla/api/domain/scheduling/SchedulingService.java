package br.com.filla.api.domain.scheduling;


import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.filla.api.config.exception.SchedulingValidationException;
import br.com.filla.api.domain.customer.CustomerRepository;
import br.com.filla.api.domain.professional.Professional;
import br.com.filla.api.domain.professional.ProfessionalRepository;
import br.com.filla.api.domain.scheduling.validations.create.SchedulingCreateValidator;
import br.com.filla.api.domain.scheduling.validations.delete.SchedulingDeleteValidator;

@Service
public class SchedulingService {

  @Autowired
  private SchedulingRepository schedulingRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private ProfessionalRepository professionalRepository;

  @Autowired
  private List<SchedulingCreateValidator> schedulingCreateValidators;

  @Autowired
  private List<SchedulingDeleteValidator> schedulingDeleteValidator;

  public Scheduling schedule(SchedulingDtoCreate dto) throws Exception {

    if (!customerRepository.existsById(dto.getCustomerId()))
      throw new SchedulingValidationException("The customer does not exist!");

    if (dto.getProfessionalId() != null && !professionalRepository.existsById(dto.getCustomerId()))
      throw new SchedulingValidationException("The professional does not exist!");

    schedulingCreateValidators.forEach(v -> {
      v.validate(dto);
    });

    var professional = chooseProfessional(dto);

    if (Objects.isNull(professional))
      throw new SchedulingValidationException(
          "There are no professionals available for scheduling.");

    var customer = customerRepository.findById(dto.getCustomerId()).get();

    var scheduling = new Scheduling(null, customer, professional, dto.getServiceDate(), null);

    schedulingRepository.save(scheduling);

    return scheduling;
  }

  public boolean cancel(Long schedulingId, SchedulingDtoDelete dto) throws Exception {

    if (!schedulingRepository.existsByIdAndReasonCancellationIsNull(schedulingId))
      return false;

    var scheduling = schedulingRepository.getReferenceById(schedulingId);
    schedulingDeleteValidator.forEach(v -> {
      v.validate(scheduling);
    });
    scheduling.cancel(dto.getReasonCancellation());

    return true;
  }

  private Professional chooseProfessional(SchedulingDtoCreate dto)
      throws SchedulingValidationException {

    if (Objects.nonNull(dto.getProfessionalId())) {
      return professionalRepository.findById(dto.getProfessionalId()).get();
    }

    if (Objects.isNull(dto.getServiceProvided()))
      throw new SchedulingValidationException(
          "The type of service provided is mandatory when the professional is not informed.");

    List<Professional> professionals =
        professionalRepository.findByServiceProvidedAndActiveTrue(dto.getServiceProvided());

    List<Professional> availableProfessionals = professionals.stream()
        .filter(p -> !schedulingRepository.existsByProfessionalIdAndServiceDateAndReasonCancellationIsNull(p.getId(),
            dto.getServiceDate()))
        .collect(Collectors.toList());

    Random rand = new Random();

    return !availableProfessionals.isEmpty()
        ? availableProfessionals.get(rand.nextInt(availableProfessionals.size()))
        : null;
  }

}
