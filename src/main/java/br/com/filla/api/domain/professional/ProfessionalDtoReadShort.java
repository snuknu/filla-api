package br.com.filla.api.domain.professional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDtoReadShort {

  private Long id;
  private String name;
  private String email;
  private String enrollment;
  private ServiceProvided serviceProvided;

  public ProfessionalDtoReadShort(Professional entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.email = entity.getEmail();
    this.enrollment = entity.getEnrollment();
    this.serviceProvided = entity.getServiceProvided();
  }

}
