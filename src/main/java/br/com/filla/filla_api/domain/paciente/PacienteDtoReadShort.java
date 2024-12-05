package br.com.filla.filla_api.domain.paciente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDtoReadShort {

  private Long id;
  private String nome;
  private String email;
  private String cpf;

  public PacienteDtoReadShort(Paciente entity) {
    this.id = entity.getId();
    this.nome = entity.getNome();
    this.email = entity.getEmail();
    this.cpf = entity.getCpf();
  }

}
