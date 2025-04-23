package programmers.nbe5_7_1_8bit.domain.inquiry.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import programmers.nbe5_7_1_8bit.domain.member.entity.Member;
import programmers.nbe5_7_1_8bit.global.common.BaseEntity;

@Entity
@Table(name="inquiry")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "inquiry_id")
  private Long id;

  private String question;
  private String answer;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Builder
  public Inquiry(String question, String answer) {
    this.question = question;
    this.answer = answer;
  }
}
