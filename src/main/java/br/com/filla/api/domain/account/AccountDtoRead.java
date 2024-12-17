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

  private Long id;
  private String username;


  public AccountDtoRead(Account entity) {
    this.id = entity.getId();
    this.username = entity.getUsername();
  }

}
