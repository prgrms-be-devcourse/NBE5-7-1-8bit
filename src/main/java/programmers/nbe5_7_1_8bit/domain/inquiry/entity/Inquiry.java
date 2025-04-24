package programmers.nbe5_7_1_8bit.domain.inquiry.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import programmers.nbe5_7_1_8bit.domain.member.entity.Member;
import programmers.nbe5_7_1_8bit.global.common.BaseEntity;

@Entity
@Table(name = "inquiry")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;

  private String title;
  private String question;
  private String answer;

  @Setter
  @Column(name = "is_removed")
  private boolean isRemoved = false;

  private String password;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToOne(
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true)
  @JoinColumn(name = "inquiry_id")
  private InquiryAnswer inquiryAnswer;

  @Builder
  public Inquiry(
      String title,
      String question,
      String answer,
      String password,
      Member member,
      InquiryAnswer inquiryAnswer) {
    this.title = title;
    this.question = question;
    this.answer = answer;
    this.password = password;
    this.member = member;
    this.inquiryAnswer = inquiryAnswer;
  }
}
