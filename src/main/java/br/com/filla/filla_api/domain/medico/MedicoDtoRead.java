package br.com.filla.filla_api.domain.medico;

import br.com.filla.filla_api.domain.endereco.EnderecoDtoRead;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicoDtoRead {

  private Long id;
  private String nome;
  private String email;
  private String telefone;
  private String crm;
  private Especialidade especialidade;
  private EnderecoDtoRead endereco;
  private Boolean active;

  public MedicoDtoRead(Medico entity) {
    this.id = entity.getId();
    this.nome = entity.getNome();
    this.email = entity.getEmail();
    this.telefone = entity.getTelefone();
    this.crm = entity.getCrm();
    this.especialidade = entity.getEspecialidade();
    this.endereco = new EnderecoDtoRead(entity.getEndereco());
    this.active = entity.getActive();
  }

}
