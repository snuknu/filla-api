package br.com.filla.filla_api.domain.scheduling;

import java.time.LocalDateTime;
import br.com.filla.filla_api.domain.customer.CustomerDtoRead;
import br.com.filla.filla_api.domain.professional.ProfessionalDtoRead;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulingDtoRead {

  private Long id;
  private CustomerDtoRead customer;
  private ProfessionalDtoRead professional;
  private LocalDateTime serviceDate;
  private ReasonCancellation reasonCancellation;

  public SchedulingDtoRead(Scheduling service) {
    this.id = service.getId();
    this.customer = new CustomerDtoRead(service.getCustomer());
    this.professional = new ProfessionalDtoRead(service.getProfessional());
    this.serviceDate = service.getServiceDate();
    this.reasonCancellation = service.getReasonCancellation();
  }

}
