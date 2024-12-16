package br.com.filla.api.domain.scheduling;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import br.com.filla.api.domain.professional.ServiceProvided;
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
public class SchedulingDtoUpdate {

  @NotNull
  private Long id;

  private Long professionalId;

  private ServiceProvided serviceProvided;

  @Future
  @JsonAlias("serviceDate")
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime serviceDate;

}
