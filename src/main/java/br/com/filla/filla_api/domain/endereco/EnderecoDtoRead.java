package br.com.filla.filla_api.domain.endereco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDtoRead {
  
  private String logradouro;
  private String bairro;
  private String cep;
  private String cidade;
  private String uf;
  private String numero;
  private String complemento;
  
  public EnderecoDtoRead(Endereco entity) {
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
