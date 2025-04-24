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
import lombok.NoArgsConstructor;
import programmers.nbe5_7_1_8bit.domain.member.entity.Member;
import programmers.nbe5_7_1_8bit.domain.order_product.entity.OrderProduct;
import programmers.nbe5_7_1_8bit.global.common.BaseEntity;

@Entity
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

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderProduct> orderProducts;

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
}
