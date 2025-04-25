package programmers.nbe5_7_1_8bit.global.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import lombok.Getter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
@FilterDef(
    name = "softDeleteFilter",
    parameters = @ParamDef(name = "isRemoved", type = Boolean.class))
public class BaseSoftDeleteEntity {

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private LocalDate createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDate updatedAt;

  @Column(name = "is_removed")
  private boolean isRemoved = false;

  public void softDelete() {
    this.isRemoved = true;
  }
}
