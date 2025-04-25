package programmers.nbe5_7_1_8bit.domain.manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import programmers.nbe5_7_1_8bit.domain.inquiry.utils.PasswordUtils;
import programmers.nbe5_7_1_8bit.global.common.BaseEntity;

@Entity
@Table(name = "managers")
@Getter
@NoArgsConstructor
public class Manager extends BaseEntity {

  @Id
  private Long id = 0L;

  @Column(nullable = false)
  private String password = PasswordUtils.encrpt("0000");

  public Manager(String password) {
    this.password = password;
  }

  public void update(String password) {
    this.password = PasswordUtils.encrpt(password);
  }
}
