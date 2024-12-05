package br.com.filla.filla_api.domain.endereco;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDtoUpdate {

  private String logradouro;
  private String bairro;

  @Pattern(regexp = "\\d{8}")
  private String cep;

  private String cidade;
  private String uf;
  private String numero;
  private String complemento;

  public EnderecoDtoUpdate(Endereco entity) {
    super();
    this.logradouro = entity.getLogradouro();
    this.bairro = entity.getBairro();
    this.cep = entity.getCep();
    this.cidade = entity.getCidade();
    this.uf = entity.getUf();
    this.numero = entity.getNumero();
    this.complemento = entity.getComplemento();
  }


}
