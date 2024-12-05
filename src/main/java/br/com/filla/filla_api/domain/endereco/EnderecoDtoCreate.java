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
public class EnderecoDtoCreate {
  
  @NotBlank
  private String logradouro;
  
  @NotBlank
  private String bairro;
  
  @NotBlank
  @Pattern(regexp = "\\d{8}")
  private String cep;
  
  @NotBlank
  private String cidade;
  
  @NotBlank
  private String uf;
  
  private String numero;
  private String complemento;

  public EnderecoDtoCreate(Endereco entity) {
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
