package br.com.filla.filla_api.domain.medico;

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
public class MedicoDtoCreate {

  @NotBlank(message = "{nome.obrigatorio}")
  private String nome;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Pattern(regexp = "\\d{11}")
  private String telefone;
  
  @NotBlank
  @Pattern(regexp = "\\d{4,6}")
  private String crm;

  @NotNull
  private Especialidade especialidade;

  @NotNull
  @Valid
  private EnderecoDtoCreate endereco;

}
