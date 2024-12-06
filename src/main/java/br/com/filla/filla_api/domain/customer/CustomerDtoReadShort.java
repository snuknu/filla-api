package br.com.filla.filla_api.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDtoReadShort {

  private Long id;
  private String name;
  private String email;

  public CustomerDtoReadShort(Customer entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
  }

}
