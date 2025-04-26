package programmers.nbe5_7_1_8bit.global.exception;

public enum ExceptionMessage {
  ILLEGAL_INQUIRY_ID("잘못된 문의 id 입니다"),
  ILLEGAL_MANAGE_ACCESS("잘못된 관리자 접근 입니다");
  private String message;

  ExceptionMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
