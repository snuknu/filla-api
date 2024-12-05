package br.com.filla.filla_api.domain.endereco;

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
public class Endereco {

  private String logradouro;
  private String bairro;
  private String cep;
  private String cidade;
  private String uf;
  private String numero;
  private String complemento;

  public Endereco(EnderecoDtoCreate dto) {
    this.logradouro = dto.getLogradouro();
    this.bairro = dto.getBairro();
    this.cep = dto.getCep();
    this.cidade = dto.getCidade();
    this.uf = dto.getUf();
    this.numero = dto.getNumero();
    this.complemento = dto.getComplemento();
  }

  public Endereco(EnderecoDtoRead dto) {
    this.logradouro = dto.getLogradouro();
    this.bairro = dto.getBairro();
    this.cep = dto.getCep();
    this.cidade = dto.getCidade();
    this.uf = dto.getUf();
    this.numero = dto.getNumero();
    this.complemento = dto.getComplemento();
  }

  public void update(EnderecoDtoUpdate dto) {
    Optional.ofNullable(dto.getLogradouro()).ifPresent(value -> this.logradouro = value);
    Optional.ofNullable(dto.getBairro()).ifPresent(value -> this.bairro = value);
    Optional.ofNullable(dto.getCep()).ifPresent(value -> this.cep = value);
    Optional.ofNullable(dto.getCidade()).ifPresent(value -> this.cidade = value);
    Optional.ofNullable(dto.getUf()).ifPresent(value -> this.uf = value);
    Optional.ofNullable(dto.getNumero()).ifPresent(value -> this.numero = value);
    Optional.ofNullable(dto.getComplemento()).ifPresent(value -> this.complemento = value);
  }

}
