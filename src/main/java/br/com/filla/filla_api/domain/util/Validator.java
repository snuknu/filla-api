package br.com.filla.filla_api.domain.util;

public abstract class Validator<T> {

  protected T dto;

  public Validator(T dto) {
    this.dto = dto;
  }

  protected abstract void validate() throws Exception;

}
