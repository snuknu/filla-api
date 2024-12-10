package br.com.filla.filla_api.domain.appointment;

import java.time.LocalDateTime;
import br.com.filla.filla_api.domain.customer.CustomerDtoReadShort;
import br.com.filla.filla_api.domain.employee.EmployeeDtoReadShort;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDtoRead {

  private Long id;
  private CustomerDtoReadShort customer;
  private EmployeeDtoReadShort employee;
  private LocalDateTime appointmentDate;
  private ReasonCancellation reasonCancellation;

  public AppointmentDtoRead(Appointment appointment) {
    this.id = appointment.getId();
    this.customer = new CustomerDtoReadShort(appointment.getCustomer());
    this.employee = new EmployeeDtoReadShort(appointment.getEmployee());
    this.appointmentDate = appointment.getAppointmentDate();
    this.reasonCancellation = appointment.getReasonCancellation();
  }

}
