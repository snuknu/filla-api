package br.com.filla.filla_api.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDtoCreate {
  
  private String username;
  private String password;
  
}
