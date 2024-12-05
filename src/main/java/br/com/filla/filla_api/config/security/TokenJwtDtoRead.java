package br.com.filla.filla_api.config.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenJwtDtoRead {
  
  private String token;
  
}
