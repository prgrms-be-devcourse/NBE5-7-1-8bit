package programmers.nbe5_7_1_8bit.domain.inquiry.entity;

import java.time.LocalDateTime;
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
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // inquiry 입력
  public InquiryDto(String title, String question, String name) {
    this.title = title;
    this.question = question;
    this.name = name;
  }

  // inquiry 조회
  public InquiryDto(Long inquiryId, String title, String question,
      String name,
      String answer,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.inquiryId = inquiryId;
    this.title = title;
    this.question = question;
    this.answer = answer;
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // inquiry 테이블 조회
  public InquiryDto(Long inquiryId, String title, String name, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.inquiryId = inquiryId;
    this.title = title;
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static Inquiry of(String title, String question, String name) {
    return Inquiry.builder().title(title).question(question).name(name).build();
  }
}
