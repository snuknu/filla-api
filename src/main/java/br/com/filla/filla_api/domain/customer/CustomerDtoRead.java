package br.com.filla.filla_api.domain.customer;

import br.com.filla.filla_api.domain.endereco.AddressDtoRead;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDtoRead {

  private Long id;
  private String name;
  private String email;
  private String phone;
  private AddressDtoRead address;
  private Boolean active;

  public CustomerDtoRead(Customer entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.address = new AddressDtoRead(entity.getAddress());
    this.active = entity.getActive();
  }
}
