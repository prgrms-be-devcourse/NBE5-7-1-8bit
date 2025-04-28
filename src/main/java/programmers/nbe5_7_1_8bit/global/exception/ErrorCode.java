package programmers.nbe5_7_1_8bit.global.exception;

public enum ErrorCode {
  ILLEGAL_INQUIRY_ID("ILLEGAL_INQUIRY_ID", "잘못된 문의 id 입니다"),
  ILLEGAL_ADMIN_ACCESS("ILLEGAL_ADMIN_ACCESS", "잘못된 관리자 접근 입니다"),
  UNEXPECTED_ERROR("UNEXPECTED_ERROR", "예상치 못한 에러입니다");
  private String code;
  private String message;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
