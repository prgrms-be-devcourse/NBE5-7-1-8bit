package programmers.nbe5_7_1_8bit.domain.order_product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import programmers.nbe5_7_1_8bit.domain.order.entity.Order;
import programmers.nbe5_7_1_8bit.global.common.BaseEntity;
import lombok.Setter;
import programmers.nbe5_7_1_8bit.domain.product.entity.Product;

@Entity
@Getter
@Table(name = "order_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends BaseEntity {
  @Id
  @Column(name = "order_product_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private int quantity;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Setter
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;


  @Builder
  public OrderProduct(int quantity, Product product) {
    this.quantity = quantity;
    this.product = product;
  }
}
