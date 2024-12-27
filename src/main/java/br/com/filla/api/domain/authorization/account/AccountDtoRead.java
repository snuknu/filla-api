package br.com.filla.api.domain.authorization.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDtoRead {

  private Long id;
  private String username;
  private Boolean active;


  public AccountDtoRead(Account entity) {
    this.id = entity.getId();
    this.username = entity.getUsername();
    this.active = entity.getActive();
  }

}
