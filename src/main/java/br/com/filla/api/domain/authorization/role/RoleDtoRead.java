package br.com.filla.api.domain.authorization.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDtoRead {

  private Long id;
  private String name;

  public RoleDtoRead(Role role) {
    this.id = role.getId();
    this.name = role.getName();
  }

}
