package br.com.filla.api.config.exception;

public class SchedulingValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public SchedulingValidationException(String message) {
    super(message);
  }

}
