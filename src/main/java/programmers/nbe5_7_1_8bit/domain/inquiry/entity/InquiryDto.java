package programmers.nbe5_7_1_8bit.domain.inquiry.entity;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InquiryDto {

  private Long inquiryId;
  private String title;
  private String question;
  private Long inquiryAnswerId;
  private String answer;
  private String name;
  private String password;
  private LocalDate createdAt;
  private LocalDate updatedAt;

  // inquiry 입력
  public InquiryDto(String title, String question) {
    this.title = title;
    this.question = question;
  }


  // inquiry 조회
  public InquiryDto(Long inquiryId, String title, String question, Long inquiryAnswerId,
      String answer,
      LocalDate createdAt,
      LocalDate updatedAt) {
    this.inquiryId = inquiryId;
    this.title = title;
    this.question = question;
    this.answer = answer;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // inquiry 테이블 조회
  public InquiryDto(Long inquiryId, String title, LocalDate createdAt, LocalDate updatedAt) {
    this.inquiryId = inquiryId;
    this.title = title;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // inquiry 수정
  public InquiryDto(Long inquiryId, String title, String question) {
    this.inquiryId = inquiryId;
    this.title = title;
    this.question = question;
  }

  public static Inquiry of(String title, String question, String name, String password) {
    return Inquiry.builder().title(title).question(question).name(name).build();
  }
}
