package br.com.filla.filla_api.domain.professional;

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
public class ProfessionalDtoCreate {

  @NotBlank(message = "{nome.obrigatorio}")
  private String name;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Pattern(regexp = "\\d{11}")
  private String phone;
  
  @NotBlank
  @Pattern(regexp = "\\d{4,6}")
  private String enrollment;

  @NotNull
  private ServiceProvided serviceProvided;

  @NotNull
  @Valid
  private AddressDtoCreate address;

}
