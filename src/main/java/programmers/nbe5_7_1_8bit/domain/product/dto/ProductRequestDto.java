package programmers.nbe5_7_1_8bit.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRequestDto {

  private String name;
  private int price;
  private int stock;
}
