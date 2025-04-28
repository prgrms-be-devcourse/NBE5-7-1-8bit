package programmers.nbe5_7_1_8bit.global.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Getter
@MappedSuperclass
@FilterDef(
    name = "softDeleteFilter",
    parameters = @ParamDef(name = "isRemoved", type = Boolean.class))
public abstract class BaseSoftDeleteEntity extends BaseEntity {

  @Column(name = "is_removed")
  private boolean isRemoved = false;

  public void softDelete() {
    this.isRemoved = true;
  }
}
