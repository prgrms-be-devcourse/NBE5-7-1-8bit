package programmers.nbe5_7_1_8bit.domain.order.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import programmers.nbe5_7_1_8bit.domain.order.dto.request.OrderRequest;
import programmers.nbe5_7_1_8bit.domain.order.dto.response.OrderDetailResponse;
import programmers.nbe5_7_1_8bit.domain.order.dto.response.OrderListResponse;
import programmers.nbe5_7_1_8bit.domain.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public void Order(@RequestParam OrderRequest orderRequest){
    orderService.save(orderRequest);
  }

  @PutMapping("/{orderId}")
  public ResponseEntity<String> updateOrder(
      @PathVariable("orderId") Long orderId,
      @RequestBody OrderRequest request
  ) {
    orderService.updateOrder(orderId, request);
    return ResponseEntity.ok("주문 수정 완료");
  }

  @PutMapping("/{orderId}/cancel/")
  public ResponseEntity<String> cancelOrder(
      @PathVariable("orderId") Long orderId,
      @RequestParam String email
  ) {
    orderService.cancelOrder(orderId, email);
    return ResponseEntity.ok("주문 취소 완료");
  }

  @GetMapping
  public ResponseEntity<List<OrderListResponse>> getOrdersByEmail(
      @RequestParam String email
  ) {
    List<OrderListResponse> orderList = orderService.getOrdersByEmail(email);
    return ResponseEntity.ok(orderList);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDetailResponse> getOrderByIdAndEmail(
      @PathVariable Long id,
      @RequestParam String email) {
    OrderDetailResponse orderDetail = orderService.getOrderByIdAndEmail(id, email);
    return ResponseEntity.ok(orderDetail);
  }
}
