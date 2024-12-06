package br.com.filla.filla_api.domain.customer;

import java.util.Optional;
import br.com.filla.filla_api.domain.endereco.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Customer")
@Table(name = "customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String phone;

  @Embedded
  private Address address;

  private Boolean active;

  public Customer(CustomerDtoCreate dto) {
    this.active = Boolean.TRUE;
    this.name = dto.getName();
    this.email = dto.getEmail();
    this.phone = dto.getPhone();
    this.address = new Address(dto.getAddress());
  }

  public void update(@Valid CustomerDtoUpdate dto) {
    Optional.ofNullable(dto.getName()).ifPresent(value -> this.name = value);
    Optional.ofNullable(dto.getPhone()).ifPresent(value -> this.phone = value);
    Optional.ofNullable(dto.getAddress()).ifPresent(value -> this.address.update(value));
  }

  public void disable() {
    this.setActive(Boolean.FALSE);
  }

}
