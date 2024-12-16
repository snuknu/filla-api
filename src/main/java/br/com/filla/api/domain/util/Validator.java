package br.com.filla.api.domain.util;

public interface Validator<T> {

  public void validate(T dto);

}
