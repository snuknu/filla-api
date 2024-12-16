package br.com.filla.api.domain.scheduling;

import java.time.LocalDateTime;
import br.com.filla.api.domain.customer.CustomerDtoRead;
import br.com.filla.api.domain.customer.CustomerDtoReadShort;
import br.com.filla.api.domain.professional.ProfessionalDtoRead;
import br.com.filla.api.domain.professional.ProfessionalDtoReadShort;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulingDtoReadShort {

  private Long id;
  private CustomerDtoReadShort customer;
  private ProfessionalDtoReadShort professional;
  private LocalDateTime serviceDate;
  private ReasonCancellation reasonCancellation;

  public SchedulingDtoReadShort(Scheduling scheduling) {
    this.id = scheduling.getId();
    this.customer = new CustomerDtoReadShort(scheduling.getCustomer());
    this.professional = new ProfessionalDtoReadShort(scheduling.getProfessional());
    this.serviceDate = scheduling.getServiceDate();
    this.reasonCancellation = scheduling.getReasonCancellation();
  }

}
