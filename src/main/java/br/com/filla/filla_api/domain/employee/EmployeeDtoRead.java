package br.com.filla.filla_api.domain.employee;

import br.com.filla.filla_api.domain.endereco.AddressDtoRead;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDtoRead {

  private Long id;
  private String name;
  private String email;
  private String phone;
  private String enrollment;
  private Service service;
  private AddressDtoRead address;
  private Boolean active;

  public EmployeeDtoRead(Employee entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.enrollment = entity.getEnrollment();
    this.service = entity.getService();
    this.address = new AddressDtoRead(entity.getAddress());
    this.active = entity.getActive();
  }

}
