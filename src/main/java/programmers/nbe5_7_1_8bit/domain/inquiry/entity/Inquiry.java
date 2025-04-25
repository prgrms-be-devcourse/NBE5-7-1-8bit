package programmers.nbe5_7_1_8bit.domain.inquiry.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import programmers.nbe5_7_1_8bit.global.common.BaseSoftDeleteEntity;

@Entity
@Table(name = "inquiry")
@Getter
@Filter(name = "softDeleteFilter", condition = "is_removed = :isRemoved")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends BaseSoftDeleteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "inquiry_id")
  private Long id;

  private String title;
  private String question;
  private String name;
  private String password;

  @OneToOne(
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true)
  @JoinColumn(name = "inquiry_answer_id")
  private InquiryAnswer inquiryAnswer;

  @Builder
  public Inquiry(String title, String question, String password, String name) {
    this.title = title;
    this.question = question;
    this.password = password;
    this.name = name;
  }

  public void update(InquiryDto inquiryDto) {
    this.title = inquiryDto.getTitle();
    this.question = inquiryDto.getQuestion();
  }

  public void answerInquiry(InquiryAnswer inquiryAnswer) {
    this.inquiryAnswer = inquiryAnswer;
  }
}
