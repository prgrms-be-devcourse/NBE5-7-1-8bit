package programmers.nbe5_7_1_8bit.global.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import programmers.nbe5_7_1_8bit.global.exception.CustomException;
import programmers.nbe5_7_1_8bit.global.exception.ErrorCode;

@ControllerAdvice
@Slf4j
public class WebExceptionHandler {

  @ExceptionHandler(Exception.class)
  public Object handleUnexpectedException(Exception e, HttpServletRequest request) {
    return processBoth(e, request,
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorCode.UNEXPECTED_ERROR),
        "/global/error");
  }

  @ExceptionHandler(CustomException.class)
  public Object handleCustomException(Exception e, HttpServletRequest request) {
    return switch (((CustomException) e).getErrorCode()) {
      case ILLEGAL_ADMIN_ACCESS -> new ModelAndView("/admin/auth/manager-auth");
      case ILLEGAL_INQUIRY_ID -> processBoth(e, request,
          ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorCode.ILLEGAL_INQUIRY_ID), "/");
      default -> processBoth(e, request,
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorCode.UNEXPECTED_ERROR),
          "/global/error");
    };
  }

  private Object processBoth(Exception e, HttpServletRequest request,
      ResponseEntity<ErrorCode> responseEntity, String htmlPath) {
    if (request.getRequestURI().startsWith("/api")) {
      return responseEntity;
    }
    log.error(e.getMessage());
    return new ModelAndView(htmlPath);
  }
}
