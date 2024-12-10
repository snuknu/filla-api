package br.com.filla.filla_api.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDtoReadShort {

  private Long id;
  private String name;
  private String email;
  private String enrollment;
  private ServiceProvided serviceProvided;

  public EmployeeDtoReadShort(Employee entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.enrollment = entity.getEnrollment();
    this.serviceProvided = entity.getServiceProvided();
  }

}
