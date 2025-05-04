package raisetech.StudentManagement.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.StudentManagement.exception.ExceptionMessage;
import raisetech.StudentManagement.exception.TestException;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(TestException.class)
  public ResponseEntity<ExceptionMessage> handlerTestException(TestException ex){
    ExceptionMessage exceptionMessage = new ExceptionMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);
  }

}
