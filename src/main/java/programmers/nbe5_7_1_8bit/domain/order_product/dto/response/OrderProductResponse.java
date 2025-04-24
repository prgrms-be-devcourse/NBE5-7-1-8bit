package programmers.nbe5_7_1_8bit.domain.order_product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import programmers.nbe5_7_1_8bit.domain.order_product.entity.OrderProduct;

@Getter
@AllArgsConstructor
public class OrderProductResponse {

  private String productName;
  private int quantity;
  private int price;

  public static OrderProductResponse from(OrderProduct orderProduct) {
    return new OrderProductResponse(
        orderProduct.getProduct().getName(),
        orderProduct.getQuantity(),
        orderProduct.getProduct().getPrice()
    );
  }
}
