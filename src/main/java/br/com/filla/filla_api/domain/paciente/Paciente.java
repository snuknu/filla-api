package br.com.filla.filla_api.domain.paciente;

import java.util.Optional;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import br.com.filla.filla_api.domain.endereco.Endereco;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Paciente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String email;
  private String telefone;
  private String cpf;

  @Embedded
  private Endereco endereco;

  private Boolean active;

  public Paciente(PacienteDtoCreate dto) {
    this.active = Boolean.TRUE;
    this.nome = dto.getNome();
    this.email = dto.getEmail();
    this.telefone = dto.getTelefone();
    this.cpf = dto.getEmail();
    this.endereco = new Endereco(dto.getEndereco());
  }

  public void update(@Valid PacienteDtoUpdate dto) {
    Optional.ofNullable(dto.getNome()).ifPresent(value -> this.nome = value);
    Optional.ofNullable(dto.getTelefone()).ifPresent(value -> this.telefone = value);
    Optional.ofNullable(dto.getEndereco()).ifPresent(value -> this.endereco.update(value));
  }

  public void disable() {
    this.setActive(Boolean.FALSE);
  }

}
