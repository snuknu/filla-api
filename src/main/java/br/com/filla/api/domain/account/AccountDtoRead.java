package br.com.filla.api.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDtoRead {

  private String username;
  private String password;

  public AccountDtoRead(Account entity) {
    this.username = entity.getUsername();
    this.password = entity.getPassword();
  }

}
