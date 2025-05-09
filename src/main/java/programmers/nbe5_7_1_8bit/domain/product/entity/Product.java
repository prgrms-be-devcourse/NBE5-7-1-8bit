package programmers.nbe5_7_1_8bit.domain.product.entity;

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
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import programmers.nbe5_7_1_8bit.global.common.BaseSoftDeleteEntity;

@Getter
@Entity
@SQLDelete(sql = "UPDATE products SET is_removed = true WHERE product_id = ?")
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Filter(name = "softDeleteFilter", condition = "is_removed = :isRemoved")
public class Product extends BaseSoftDeleteEntity {

  @Id
  @Column(name = "product_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private int price;
  private int stock;

  @Setter
  @Column(name = "image_path")
  private String imagePath;

  @Builder
  public Product(String name, int price, int stock) {
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

  public void update(String name, int price, int stock) {
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

  public void decreaseStock(int quantity) {
    if (this.stock < quantity) {
      throw new IllegalStateException("상품 재고가 부족합니다. 상품명: " + name);
    }
    this.stock -= quantity;
  }

  public void increaseStock(int quantity) {
    this.stock += quantity;
  }

}
