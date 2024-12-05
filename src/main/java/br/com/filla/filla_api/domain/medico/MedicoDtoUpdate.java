package br.com.filla.filla_api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import br.com.filla.filla_api.domain.endereco.EnderecoDtoUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicoDtoUpdate {

  @NotNull
  private Long id;

  private String nome;
  private String telefone;

  @Valid
  private EnderecoDtoUpdate endereco;

}
