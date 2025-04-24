package programmers.nbe5_7_1_8bit.domain.member.entity;

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
import programmers.nbe5_7_1_8bit.global.common.BaseEntity;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long Id;

  @Column(nullable = false, length = 100)
  private String email;

  @Column(nullable = false)
  private boolean is_removed = false;

  @Builder
  public Member(String email) {
    this.email = email;
  }
}
