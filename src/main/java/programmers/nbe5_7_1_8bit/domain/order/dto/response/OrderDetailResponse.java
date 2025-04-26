package programmers.nbe5_7_1_8bit.domain.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import programmers.nbe5_7_1_8bit.domain.order.entity.Order;
import programmers.nbe5_7_1_8bit.domain.order.entity.Status;
import programmers.nbe5_7_1_8bit.domain.order_product.dto.response.OrderProductResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import programmers.nbe5_7_1_8bit.domain.order_product.entity.OrderProduct;
import programmers.nbe5_7_1_8bit.domain.order_product.repository.OrderProductRepository;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {

  private Long id;
  private String email;
  private String address;
  private String postcode;
  private Status status;
  private LocalDate createdAt;
  private List<OrderProductResponse> orderProducts;

  public static OrderDetailResponse from(Order order, OrderProductRepository repository) {

    List<OrderProduct> orderProducts = repository.findOrderProductByOrder(order);
    List<OrderProductResponse> orderProductResponses = orderProducts.stream()
        .map(OrderProductResponse::from)
        .collect(Collectors.toList());

    return new OrderDetailResponse(
        order.getId(),
        order.getMember().getEmail(),
        order.getAddress(),
        order.getPostcode(),
        order.getStatus(),
        order.getCreatedAt(),
        orderProductResponses
    );
  }
}
