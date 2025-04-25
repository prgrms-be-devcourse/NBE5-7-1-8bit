package programmers.nbe5_7_1_8bit.domain.inquiry.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import programmers.nbe5_7_1_8bit.global.common.BaseSoftDeleteEntity;

@Entity
@Table(name = "inquiry_answer")
@Getter
@Filter(name = "softDeleteFilter", condition = "is_removed = :isRemoved")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryAnswer extends BaseSoftDeleteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "inquiry_answer_id")
  private Long id;

  private String answer;

  @Builder
  public InquiryAnswer(String answer) {
    this.answer = answer;
  }

  public void update(InquiryAnswerDto inquiryAnswerDto) {
    this.answer = inquiryAnswerDto.getAnswer();
  }
}
