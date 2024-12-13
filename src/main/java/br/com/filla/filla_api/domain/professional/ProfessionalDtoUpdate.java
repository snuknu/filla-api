package br.com.filla.filla_api.domain.professional;

import br.com.filla.filla_api.domain.address.AddressDtoUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDtoUpdate {

  @NotNull
  private Long id;

  private String name;
  private String phone;

  @Valid
  private AddressDtoUpdate address;

}
