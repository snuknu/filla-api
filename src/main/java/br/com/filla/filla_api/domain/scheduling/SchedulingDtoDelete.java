package br.com.filla.filla_api.domain.scheduling;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulingDtoDelete {

  @NotNull
  private ReasonCancellation reasonCancellation;

}
