package br.com.filla.filla_api.domain.medico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicoDtoReadShort {

  private Long id;
  private String nome;
  private String email;
  private String crm;
  private Especialidade especialidade;

  public MedicoDtoReadShort(Medico entity) {
    this.id = entity.getId();
    this.nome = entity.getNome();
    this.email = entity.getEmail();
    this.crm = entity.getCrm();
    this.especialidade = entity.getEspecialidade();
  }

}
