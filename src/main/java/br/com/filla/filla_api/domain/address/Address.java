package br.com.filla.filla_api.domain.address;

import java.util.Optional;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

  private String street;
  private String district;
  private String zipcode;
  private String city;
  private String state;
  private String number;
  private String complement;

  public Address(AddressDtoCreate dto) {
    this.street = dto.getStreet();
    this.district = dto.getDistrict();
    this.zipcode = dto.getZipcode();
    this.city = dto.getCity();
    this.state = dto.getState();
    this.number = dto.getNumber();
    this.complement = dto.getComplement();
  }

  public Address(AddressDtoRead dto) {
    this.street = dto.getStreet();
    this.district = dto.getDistrict();
    this.zipcode = dto.getZipcode();
    this.city = dto.getCity();
    this.state = dto.getState();
    this.number = dto.getNumber();
    this.complement = dto.getComplement();
  }

  public void update(AddressDtoUpdate dto) {
    Optional.ofNullable(dto.getStreet()).ifPresent(value -> this.street = value);
    Optional.ofNullable(dto.getDistrict()).ifPresent(value -> this.district = value);
    Optional.ofNullable(dto.getZipcode()).ifPresent(value -> this.zipcode = value);
    Optional.ofNullable(dto.getCity()).ifPresent(value -> this.city = value);
    Optional.ofNullable(dto.getState()).ifPresent(value -> this.state = value);
    Optional.ofNullable(dto.getNumber()).ifPresent(value -> this.number = value);
    Optional.ofNullable(dto.getComplement()).ifPresent(value -> this.complement = value);
  }

}
