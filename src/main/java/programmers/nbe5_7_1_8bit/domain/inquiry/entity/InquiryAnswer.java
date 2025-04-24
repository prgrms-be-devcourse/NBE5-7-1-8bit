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
import lombok.Setter;
import programmers.nbe5_7_1_8bit.global.common.BaseEntity;

@Entity
@Table(name = "inquiry_answer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryAnswer extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "inquiry_answer_id")
  private Long id;

  private String answer;

  @Setter
  @Column(name = "is_removed")
  private boolean isRemoved;

  @Builder
  public InquiryAnswer(String answer) {
    this.answer = answer;
    this.isRemoved = false;
  }
}
