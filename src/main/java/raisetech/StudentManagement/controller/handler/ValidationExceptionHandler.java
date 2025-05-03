package raisetech.StudentManagement.controller.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler{

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handlerValidationException(){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("クエリパラメータの入力エラーです。1~3桁の数字を指定してください。");
  }

}