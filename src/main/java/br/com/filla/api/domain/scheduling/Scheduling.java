package br.com.filla.api.domain.scheduling;

import java.time.LocalDateTime;
import br.com.filla.api.domain.customer.Customer;
import br.com.filla.api.domain.professional.Professional;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Scheduling")
@Table(name = "scheduling")
public class Scheduling {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "professional_id")
  private Professional professional;

  @Column(name = "service_date")
  private LocalDateTime serviceDate;

  @Column(name = "reason_cancellation")
  @Enumerated(EnumType.STRING)
  private ReasonCancellation reasonCancellation;

  public void cancel(ReasonCancellation reasonCancellation) {
    this.reasonCancellation = reasonCancellation;
  }
}
