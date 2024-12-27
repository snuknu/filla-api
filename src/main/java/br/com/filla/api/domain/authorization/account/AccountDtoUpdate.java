package br.com.filla.api.domain.authorization.account;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDtoUpdate {

  @NotNull
  private Long id;

  private String username;
  private String password;

}
