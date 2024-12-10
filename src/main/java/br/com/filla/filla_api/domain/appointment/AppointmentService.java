package br.com.filla.filla_api.domain.appointment;


import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.filla.filla_api.config.exception.AppointmentValidationException;
import br.com.filla.filla_api.domain.customer.CustomerRepository;
import br.com.filla.filla_api.domain.employee.Employee;
import br.com.filla.filla_api.domain.employee.EmployeeRepository;

@Service
public class AppointmentService {

  @Autowired
  private AppointmentRepository appointmentRepository;

  private CustomerRepository customerRepository;

  private EmployeeRepository employeeRepository;


  public Appointment schedule(AppointmentDtoCreate dto) throws Exception {

    if (!customerRepository.existsById(dto.getCustomerId()))
      throw new AppointmentValidationException("The client does not exist!");

    var customer = customerRepository.findById(dto.getCustomerId()).get();

    var employee = chooseEmployee(dto);

    // employeeRepository.findById(dto.getEmployeeId()).get();

    var appointment = new Appointment(null, customer, employee, dto.getAppointmentDate(), null);

    appointmentRepository.save(appointment);

    return appointment;
  }


  private Employee chooseEmployee(AppointmentDtoCreate dto) throws AppointmentValidationException {

    if (employeeRepository.existsById(dto.getEmployeeId()))
      return employeeRepository.getReferenceById(dto.getEmployeeId());

    if (Objects.nonNull(dto.getServiceProvided()))
      throw new AppointmentValidationException(
          "The type of service provided is mandatory when the employee is not informed.");

    List<Employee> employees =
        employeeRepository.findByServiceProvidedAndActiveTrue(dto.getServiceProvided());


    List<Employee> availableEmployees = employees.stream().filter(item -> {
      return appointmentRepository
          .findByEmployeeIdAndAppointmentDateBetweenAndReasonCancellationIsNull(
              item.getId(), dto.getAppointmentDate(),
              dto.getAppointmentDate().plusHours(1))
          .isEmpty();
    }).collect(Collectors.toList());



    Random rand = new Random();

    return !availableEmployees.isEmpty()
        ? availableEmployees.get(rand.nextInt(availableEmployees.size()))
        : null;
  }

}
