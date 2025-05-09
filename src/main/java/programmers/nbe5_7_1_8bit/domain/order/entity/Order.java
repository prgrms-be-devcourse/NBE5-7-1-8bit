package programmers.nbe5_7_1_8bit.domain.order.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import programmers.nbe5_7_1_8bit.domain.member.entity.Member;
import programmers.nbe5_7_1_8bit.domain.order_product.entity.OrderProduct;
import programmers.nbe5_7_1_8bit.global.common.BaseEntity;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="order_id")
  private Long id;

  private String address;
  private String postcode;

  @Enumerated(EnumType.STRING)
  private Status status;
  private boolean is_removed = false;

  @ManyToOne
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Builder
  public Order(String address, String postcode, Status status, Member member) {
    this.address = address;
    this.postcode = postcode;
    this.status = status;
    this.member = member;
  }

  public void updateAddress(String address, String postcode) {
    this.address = address;
    this.postcode = postcode;
  }

  public void cancelOrder() {
    this.status = Status.CANCELED;
  }

  public boolean isOwnedBy(String email) {
    return this.member != null && this.member.getEmail().equals(email);
  }
  public void updateStatus(Status status) {
    this.status = status;
  }
  public void updateEmail(String email) {
    this.member.setEmail(email);
  }

}
