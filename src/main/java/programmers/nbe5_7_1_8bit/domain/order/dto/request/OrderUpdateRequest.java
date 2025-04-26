package programmers.nbe5_7_1_8bit.domain.order.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import programmers.nbe5_7_1_8bit.domain.order.entity.Status;
import programmers.nbe5_7_1_8bit.domain.order_product.dto.request.OrderProductRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequest {

  private String email;
  private Status status;
  private String address;
  private String postcode;

  private List<OrderProductRequest> orderProducts;

}
