package br.com.filla.filla_api.domain.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDtoDelete {

  @NotNull
  private Long appointmentId;

  @NotNull
  private ReasonCancellation reasonCancellation;

}
