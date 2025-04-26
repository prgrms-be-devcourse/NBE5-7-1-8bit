package programmers.nbe5_7_1_8bit.domain.inquiry.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InquiryAnswerDto {

  private Long inquiryId;
  private String answer;

  public InquiryAnswerDto(Long inquiryId, String answer) {
    this.inquiryId = inquiryId;
    this.answer = answer;
  }

  public static InquiryAnswer of(String answer) {
    return InquiryAnswer.builder().answer(answer).build();
  }
}
