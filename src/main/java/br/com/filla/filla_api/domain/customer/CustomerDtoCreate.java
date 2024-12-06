package br.com.filla.filla_api.domain.customer;

import br.com.filla.filla_api.domain.endereco.AddressDtoCreate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDtoCreate {

  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Pattern(regexp = "\\d{11}")
  private String phone;
  
  @NotNull
  @Valid
  private AddressDtoCreate address;

}
