package br.com.filla.filla_api.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.auth0.jwt.exceptions.JWTCreationException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> handleError404() {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleError400(
      MethodArgumentNotValidException ex) {
    var errors = ex.getFieldErrors();
    return ResponseEntity.badRequest()
        .body(new ErrorDto(errors.stream().map(ArgumentNotValidError::new).toList()));
  }

  @ExceptionHandler(JWTCreationException.class)
  public ResponseEntity<ErrorDto> handleError400(JWTCreationException ex) {
    return ResponseEntity.badRequest()
        .body(new ErrorDto("Token cannot be created."));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorDto> handleError400(HttpMessageNotReadableException ex) {
    return ResponseEntity.badRequest()
        .body(new ErrorDto("Error reading received data."));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorDto> handleErrorBadCredentials() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ErrorDto("Invalid credentials."));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorDto> handleErrorAuthentication() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ErrorDto("Authentication failed."));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorDto> handleErrorDeniedException() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(new ErrorDto("Access denied."));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleError500(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorDto(ex.getLocalizedMessage()));
  }

  @ExceptionHandler(SchedulingValidationException.class)
  public ResponseEntity<ErrorDto> handleServiceValidationException(SchedulingValidationException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorDto(ex.getLocalizedMessage()));
  }
  
  
  private record ArgumentNotValidError(String field, String message) {
    public ArgumentNotValidError(FieldError error) {
      this(error.getField(), error.getDefaultMessage());
    }
  }

  private record ErrorDto(Object error) {

  }

}
