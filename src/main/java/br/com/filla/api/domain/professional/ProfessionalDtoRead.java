package br.com.filla.api.domain.professional;

import br.com.filla.api.domain.address.AddressDtoRead;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDtoRead {

  private Long id;
  private String name;
  private String email;
  private String phone;
  private String enrollment;
  private ServiceProvided serviceProvided;
  private AddressDtoRead address;
  private Boolean active;

  public ProfessionalDtoRead(Professional entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.enrollment = entity.getEnrollment();
    this.serviceProvided = entity.getServiceProvided();
    this.address = new AddressDtoRead(entity.getAddress());
    this.active = entity.getActive();
  }

}
