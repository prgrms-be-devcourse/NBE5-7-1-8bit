package programmers.nbe5_7_1_8bit.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import programmers.nbe5_7_1_8bit.global.common.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

  @Id
  @Column(name = "product_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private int price;

  private int stock;

  @Column(name = "is_removed")
  private boolean isRemoved = false;

  @Builder
  public Product(String name, int price, int stock){
    this.name = name;
    this.price = price;
    this.stock = stock;
    
  }

  public void update(String name, int price, int stock) {
    this.name = name;
    this.price = price;
    this.stock = stock;
  }
}
