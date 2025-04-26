package programmers.nbe5_7_1_8bit.global.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import programmers.nbe5_7_1_8bit.global.exception.CustomException;

@ControllerAdvice
public class WebExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public String handleIllegalAccess(CustomException e, Model model) {
    model.addAttribute("errorMessage", e.getMessage());
    return "/global/error";
  }

}
