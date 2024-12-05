package br.com.filla.filla_api.domain.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDtoUpdate {
  
  @NotBlank
  private String username;
  
  @NotBlank
  private String password;
  
}
