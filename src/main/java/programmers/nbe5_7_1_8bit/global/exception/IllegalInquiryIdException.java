package programmers.nbe5_7_1_8bit.global.exception;

public class IllegalInquiryIdException extends RuntimeException {

  public IllegalInquiryIdException() {
    super("잘못된 문의 id");
  }
}
