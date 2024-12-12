package br.com.filla.filla_api.domain.appointment;

import java.time.LocalDateTime;
import br.com.filla.filla_api.domain.customer.CustomerDtoReadShort;
import br.com.filla.filla_api.domain.professional.ProfessionalDtoReadShort;
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
  private ProfessionalDtoReadShort professional;
  private LocalDateTime appointmentDate;
  private ReasonCancellation reasonCancellation;

  public AppointmentDtoRead(Appointment appointment) {
    this.id = appointment.getId();
    this.customer = new CustomerDtoReadShort(appointment.getCustomer());
    this.professional = new ProfessionalDtoReadShort(appointment.getProfessional());
    this.appointmentDate = appointment.getAppointmentDate();
    this.reasonCancellation = appointment.getReasonCancellation();
  }

}
