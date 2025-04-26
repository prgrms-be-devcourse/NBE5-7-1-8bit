package programmers.nbe5_7_1_8bit.global.exception;

public class CustomException extends RuntimeException {

  public CustomException(ExceptionMessage exception) {
    super(exception.getMessage());
  }
}
