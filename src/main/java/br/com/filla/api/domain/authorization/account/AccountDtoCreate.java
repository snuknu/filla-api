package br.com.filla.api.domain.authorization.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDtoCreate {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

}
