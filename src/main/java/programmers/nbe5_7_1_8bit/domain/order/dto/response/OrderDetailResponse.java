package programmers.nbe5_7_1_8bit.domain.order.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import programmers.nbe5_7_1_8bit.domain.order.entity.Order;
import programmers.nbe5_7_1_8bit.domain.order.entity.Status;
import programmers.nbe5_7_1_8bit.domain.order_product.dto.response.OrderProductResponse;
import programmers.nbe5_7_1_8bit.domain.order_product.entity.OrderProduct;
import programmers.nbe5_7_1_8bit.domain.order_product.repository.OrderProductRepository;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {

  private String address;
  private String postcode;
  private Status status;
  private LocalDateTime createdAt;
  private List<OrderProductResponse> orderProducts;

  public static OrderDetailResponse from(Order order, OrderProductRepository repository) {

    List<OrderProduct> orderProducts = repository.findOrderProductByOrder(order);
    List<OrderProductResponse> orderProductResponses = orderProducts.stream()
        .map(OrderProductResponse::from)
        .collect(Collectors.toList());

    return new OrderDetailResponse(
        order.getAddress(),
        order.getPostcode(),
        order.getStatus(),
        order.getCreatedAt(),
        orderProductResponses
    );
  }
}
