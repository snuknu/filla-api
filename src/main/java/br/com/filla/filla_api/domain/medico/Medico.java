package br.com.filla.filla_api.domain.medico;

import java.util.Optional;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Entity(name = "Medico")
@Table(name = "medicos")
public class Medico {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String email;
  private String telefone;
  private String crm;

  @Enumerated(EnumType.STRING)
  private Especialidade especialidade;

  @Embedded
  private Endereco endereco;

  private Boolean active;

  public Medico(MedicoDtoCreate dto) {
    this.active = Boolean.TRUE;
    this.nome = dto.getNome();
    this.email = dto.getEmail();
    this.telefone = dto.getTelefone();
    this.crm = dto.getCrm();
    this.especialidade = dto.getEspecialidade();
    this.endereco = new Endereco(dto.getEndereco());
  }

  public void update(@Valid MedicoDtoUpdate dto) {
    Optional.ofNullable(dto.getNome()).ifPresent(value -> this.nome = value);
    Optional.ofNullable(dto.getTelefone()).ifPresent(value -> this.telefone = value);
    Optional.ofNullable(dto.getEndereco()).ifPresent(value -> this.endereco.update(value));
  }

  public void disable() {
    this.setActive(Boolean.FALSE);
  }

}
