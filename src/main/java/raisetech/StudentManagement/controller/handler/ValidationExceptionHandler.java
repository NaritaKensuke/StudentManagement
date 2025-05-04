package raisetech.StudentManagement.controller.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.StudentManagement.exception.ExceptionMessage;

@RestControllerAdvice
public class ValidationExceptionHandler{

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ExceptionMessage> handlerConstraintViolationException(
      ConstraintViolationException ex){
    ExceptionMessage exceptionMessage =
        new ExceptionMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);
  }

}