package br.com.filla.filla_api.domain.paciente;

import br.com.filla.filla_api.domain.endereco.EnderecoDtoRead;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDtoRead {

  private Long id;
  private String nome;
  private String email;
  private String telefone;
  private String cpf;
  private EnderecoDtoRead endereco;
  private Boolean active;

  public PacienteDtoRead(Paciente entity) {
    this.id = entity.getId();
    this.nome = entity.getNome();
    this.email = entity.getEmail();
    this.telefone = entity.getTelefone();
    this.cpf = entity.getCpf();
    this.endereco = new EnderecoDtoRead(entity.getEndereco());
    this.active = entity.getActive();
  }
}
