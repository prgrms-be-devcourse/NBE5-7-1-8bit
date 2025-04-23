package programmers.nbe5_7_1_8bit.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import programmers.nbe5_7_1_8bit.domain.product.entity.Product;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseDto {

  private Long id;
  private String name;
  private int price;
  private int stock;

  public static ProductResponseDto from(Product product) {
    return ProductResponseDto.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .stock(product.getStock())
        .build();
  }
}
