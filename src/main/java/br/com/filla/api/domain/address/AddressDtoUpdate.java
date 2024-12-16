package br.com.filla.api.domain.address;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDtoUpdate {

  private String street;
  private String district;

  @Pattern(regexp = "\\d{8}")
  private String zipcode;

  private String city;
  private String state;
  private String number;
  private String complement;

  public AddressDtoUpdate(Address entity) {
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
