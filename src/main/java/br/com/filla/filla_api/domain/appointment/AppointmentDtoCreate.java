package br.com.filla.filla_api.domain.appointment;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import br.com.filla.filla_api.domain.professional.ServiceProvided;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDtoCreate {

  @NotNull
  private Long customerId;

  private Long professionalId;

  private ServiceProvided serviceProvided;

  @NotNull
  @Future
  @JsonAlias("appointmentDate")
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime appointmentDate;

}
