package br.com.filla.filla_api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import br.com.filla.filla_api.domain.endereco.EnderecoDtoCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDtoCreate {

  @NotBlank
  private String nome;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Pattern(regexp = "\\d{11}")
  private String telefone;
  
  @NotBlank
  @Pattern(regexp = "\\d{11}")
  private String cpf;

  @NotNull
  @Valid
  private EnderecoDtoCreate endereco;

}
