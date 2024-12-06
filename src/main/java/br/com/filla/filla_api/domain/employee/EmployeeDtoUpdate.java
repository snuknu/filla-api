package br.com.filla.filla_api.domain.employee;

import br.com.filla.filla_api.domain.endereco.AddressDtoUpdate;
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
public class EmployeeDtoUpdate {

  @NotNull
  private Long id;

  private String name;
  private String phone;

  @Valid
  private AddressDtoUpdate address;

}
