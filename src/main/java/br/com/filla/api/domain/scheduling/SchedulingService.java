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
import br.com.filla.api.domain.scheduling.validations.SchedulingValidator;

@Service
public class SchedulingService {

  @Autowired
  private SchedulingRepository schedulingRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private ProfessionalRepository professionalRepository;

  @Autowired
  private List<SchedulingValidator> schedulingValidators;


  public Scheduling schedule(SchedulingDtoCreate dto) throws Exception {

    if (!customerRepository.existsById(dto.getCustomerId()))
      throw new SchedulingValidationException("The customer does not exist!");

    if (dto.getProfessionalId() != null && !professionalRepository.existsById(dto.getCustomerId()))
      throw new SchedulingValidationException("The professional does not exist!");

    schedulingValidators.forEach(v -> {
      v.validate(dto);
    });

    var professional = chooseProfessional(dto);

    var customer = customerRepository.findById(dto.getCustomerId()).get();

    var scheduling = new Scheduling(null, customer, professional, dto.getServiceDate(), null);

    schedulingRepository.save(scheduling);

    return scheduling;
  }

  public Scheduling reschedule(SchedulingDtoUpdate dto) throws Exception {
    return schedulingRepository.getReferenceById(dto.getId());
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
        .filter(p -> !schedulingRepository.existsByProfessionalIdAndServiceDate(p.getId(),
            dto.getServiceDate()))
        .collect(Collectors.toList());

    if (availableProfessionals.isEmpty())
      throw new SchedulingValidationException(
          "There are no professionals available for scheduling.");

    Random rand = new Random();

    return !availableProfessionals.isEmpty()
        ? availableProfessionals.get(rand.nextInt(availableProfessionals.size()))
        : null;
  }

}
