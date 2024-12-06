package br.com.filla.filla_api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDtoCreate {
  
  @NotBlank
  private String street;
  
  @NotBlank
  private String district;
  
  @NotBlank
  @Pattern(regexp = "\\d{8}")
  private String zipcode;
  
  @NotBlank
  private String city;
  
  @NotBlank
  private String state;
  
  private String number;
  private String complement;

  public AddressDtoCreate(Address entity) {
    super();
    this.street = entity.getStreet();
    this.district = entity.getDistrict();
    this.zipcode = entity.getZipcode();
    this.city = entity.getCity();
    this.state = entity.getState();
    this.number = entity.getNumber();
    this.complement = entity.getComplement();
  }
  
  
}
