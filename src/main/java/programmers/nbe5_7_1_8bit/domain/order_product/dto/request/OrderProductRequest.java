package programmers.nbe5_7_1_8bit.domain.order_product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRequest {

  private Long productId;
  private int quantity;

}
