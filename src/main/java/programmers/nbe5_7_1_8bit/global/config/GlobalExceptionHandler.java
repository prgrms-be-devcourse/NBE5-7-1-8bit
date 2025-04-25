package programmers.nbe5_7_1_8bit.global.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import programmers.nbe5_7_1_8bit.global.exception.IllegalInquiryIdException;
import programmers.nbe5_7_1_8bit.global.exception.IllegalManagerAccessException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalManagerAccessException.class)
  public ResponseEntity handleIllegalAccess(IllegalManagerAccessException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @ExceptionHandler(IllegalInquiryIdException.class)
  public ResponseEntity handleIllegalInquiryId(IllegalInquiryIdException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }
}
