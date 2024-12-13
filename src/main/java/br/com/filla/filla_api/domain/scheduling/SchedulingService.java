package br.com.filla.filla_api.domain.scheduling;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.filla.filla_api.config.exception.SchedulingValidationException;
import br.com.filla.filla_api.domain.customer.CustomerRepository;
import br.com.filla.filla_api.domain.professional.Professional;
import br.com.filla.filla_api.domain.professional.ProfessionalRepository;

@Service
public class SchedulingService {

  @Autowired
  private SchedulingRepository schedulingRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private ProfessionalRepository professionalRepository;


  public Scheduling schedule(SchedulingDtoCreate dto) throws Exception {

    if (!customerRepository.existsById(dto.getCustomerId()))
      throw new SchedulingValidationException("The customer does not exist!");

    var customer = customerRepository.findById(dto.getCustomerId()).get();

    var professional = chooseProfessional(dto);

    if (Objects.isNull(professional))
      throw new SchedulingValidationException("professional does not exist!");

    var service = new Scheduling(null, customer, professional, dto.getServiceDate(), null);

    schedulingRepository.save(service);

    return service;
  }

  public Scheduling reschedule(SchedulingDtoUpdate dto) throws Exception {
    return schedulingRepository.getReferenceById(dto.getId());
  }


  private Professional chooseProfessional(SchedulingDtoCreate dto)
      throws SchedulingValidationException {

    if (Objects.nonNull(dto.getProfessionalId())) {

      if (!professionalRepository.existsById(dto.getProfessionalId()))
        throw new SchedulingValidationException("The professional does not exist!");

      if (!hasSchedulingConflict(dto.getProfessionalId(), dto.getServiceDate(),
          dto.getServiceDate().plusHours(1)))
        return professionalRepository.findById(dto.getProfessionalId()).get();
      else
        throw new SchedulingValidationException("Professional not available for the scheduled date!");
    }

    if (Objects.isNull(dto.getServiceProvided()))
      throw new SchedulingValidationException(
          "The type of service provided is mandatory when the professional is not informed.");

    List<Professional> professionals =
        professionalRepository.findByServiceProvidedAndActiveTrue(dto.getServiceProvided());

    List<Professional> availableProfessionals = professionals.stream()
        .filter(item -> !hasSchedulingConflict(item.getId(), dto.getServiceDate(),
            dto.getServiceDate().plusMinutes(60)))
        .collect(Collectors.toList());

    if (availableProfessionals.isEmpty())
      throw new SchedulingValidationException(
          "There are no professionals available for scheduling.");

    Random rand = new Random();

    return !availableProfessionals.isEmpty()
        ? availableProfessionals.get(rand.nextInt(availableProfessionals.size()))
        : null;
  }

  private boolean hasSchedulingConflict(
      Long professionalId,
      LocalDateTime startDate,
      LocalDateTime endDate) {

    return !schedulingRepository
        .findByProfessionalIdAndSchedulingDateBetweenAndReasonCancellationIsNull(
            professionalId, startDate, endDate)
        .isEmpty();
  }

}
