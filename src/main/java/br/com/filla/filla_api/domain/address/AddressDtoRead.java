package br.com.filla.filla_api.domain.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDtoRead {

  private String street;
  private String district;
  private String zipcode;
  private String city;
  private String state;
  private String number;
  private String complement;

  public AddressDtoRead(Address entity) {
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
