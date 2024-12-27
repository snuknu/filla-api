package br.com.filla.api.domain.authorization.role;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import br.com.filla.api.domain.authorization.account.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Entity(name = "Role")
@Table(name = "role")
public class Role implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany(mappedBy = "roles")
  private List<Account> accounts;

  public Role(RoleDtoWrite dto) {
    Optional.ofNullable(dto.getId()).ifPresent(value -> this.id = value);
    this.name = dto.getName();
  }

}
