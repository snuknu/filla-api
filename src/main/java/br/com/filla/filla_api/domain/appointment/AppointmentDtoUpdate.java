package br.com.filla.filla_api.domain.appointment;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import br.com.filla.filla_api.domain.employee.ServiceProvided;
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
public class AppointmentDtoUpdate {

  @NotNull
  private Long id;

  private Long employeeId;

  private ServiceProvided serviceProvided;

  @Future
  @JsonAlias("appointmentDate")
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime appointmentDate;

}
