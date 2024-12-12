package br.com.filla.filla_api.domain.appointment;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.filla.filla_api.config.exception.AppointmentValidationException;
import br.com.filla.filla_api.domain.customer.CustomerRepository;
import br.com.filla.filla_api.domain.professional.Professional;
import br.com.filla.filla_api.domain.professional.ProfessionalRepository;

@Service
public class AppointmentService {

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private ProfessionalRepository professionalRepository;


  public Appointment schedule(AppointmentDtoCreate dto) throws Exception {

    if (!customerRepository.existsById(dto.getCustomerId()))
      throw new AppointmentValidationException("The customer does not exist!");

    var customer = customerRepository.findById(dto.getCustomerId()).get();

    var professional = chooseProfessional(dto);

    if (Objects.isNull(professional))
      throw new AppointmentValidationException("professional does not exist!");

    var appointment = new Appointment(null, customer, professional, dto.getAppointmentDate(), null);

    appointmentRepository.save(appointment);

    return appointment;
  }


  private Professional chooseProfessional(AppointmentDtoCreate dto)
      throws AppointmentValidationException {

    if (Objects.nonNull(dto.getProfessionalId())) {

      if (!professionalRepository.existsById(dto.getCustomerId()))
        throw new AppointmentValidationException("The professional does not exist!");

      if (!hasSchedulingConflict(dto.getProfessionalId(), dto.getAppointmentDate(),
          dto.getAppointmentDate().plusHours(1)))
        return professionalRepository.findById(dto.getProfessionalId()).get();
      else
        throw new AppointmentValidationException("professional does not exist!");

    }

    if (Objects.isNull(dto.getServiceProvided()))
      throw new AppointmentValidationException(
          "The type of service provided is mandatory when the professional is not informed.");

    List<Professional> professionals =
        professionalRepository.findByServiceProvidedAndActiveTrue(dto.getServiceProvided());

    List<Professional> availableProfessionals = professionals.stream()
        .filter(item -> !hasSchedulingConflict(item.getId(), dto.getAppointmentDate(),
            dto.getAppointmentDate().plusHours(1)))
        .collect(Collectors.toList());

    if (availableProfessionals.isEmpty())
      throw new AppointmentValidationException(
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

    return !appointmentRepository
        .findByProfessionalIdAndAppointmentDateBetweenAndReasonCancellationIsNull(
            professionalId, startDate, endDate)
        .isEmpty();
  }

}
