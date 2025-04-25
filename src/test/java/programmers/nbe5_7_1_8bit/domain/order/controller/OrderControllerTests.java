package programmers.nbe5_7_1_8bit.domain.order.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import programmers.nbe5_7_1_8bit.domain.order.dto.request.OrderRequest;
import programmers.nbe5_7_1_8bit.domain.order.dto.response.OrderDetailResponse;
import programmers.nbe5_7_1_8bit.domain.order.dto.response.OrderListResponse;
import programmers.nbe5_7_1_8bit.domain.order.entity.Status;
import programmers.nbe5_7_1_8bit.domain.order.service.OrderService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private OrderService orderService;

  @InjectMocks
  private OrderController orderController;

  private OrderRequest orderRequest;
  private OrderListResponse orderListResponse;
  private OrderDetailResponse orderDetailResponse;

  @BeforeEach
  public void setup() {
    orderRequest = new OrderRequest(
        "test@example.com",
        "addressTest",
        "12345",
        Collections.emptyList()
    );

    orderListResponse = new OrderListResponse(
        "test@example.com",
        1L,
        Status.BEFORE_SHIPPING,
        LocalDate.now(),
        Collections.emptyList()
    );

    orderDetailResponse = new OrderDetailResponse(
        "addressTest",
        "12345",
        Status.BEFORE_SHIPPING,
        LocalDate.now(),
        Collections.emptyList()
    );
  }

  @Test
  @DisplayName("주문 추가 API 테스트")
  void save_order_test() throws Exception {

    doNothing().when(orderService).save(any(OrderRequest.class));

    mockMvc.perform(post("/api/order")
            .contentType("application/json")
            .content("{\"email\":\"test@example.com\", \"address\":\"addressTest\", \"postcode\":\"12345\", \"orderProducts\":[]}"))
        .andExpect(status().isOk());

    verify(orderService, times(1)).save(any(OrderRequest.class));
  }

  @Test
  @DisplayName("주문 수정 API 테스트")
  void update_order_test() throws Exception {

    doNothing().when(orderService).updateOrder(any(Long.class), any(OrderRequest.class));

    mockMvc.perform(put("/api/order/{orderId}", 1L)
            .contentType("application/json")
            .content("{\"email\":\"updateTest@example.com\",\"address\":\"updateTest\",\"postcode\":\"54321\",\"orderProducts\":[]}"))
        .andExpect(status().isOk());

    verify(orderService, times(1)).updateOrder(any(Long.class), any(OrderRequest.class));
  }

  @Test
  @DisplayName("주문 취소 API 테스트")
  void cancel_order_test() throws Exception {

    doNothing().when(orderService).cancelOrder(any(Long.class), any(String.class));

    mockMvc.perform(put("/api/order/{orderId}/cancel", 1L)
            .param("email", orderRequest.getEmail()))
        .andExpect(status().isOk());

    verify(orderService, times(1)).cancelOrder(any(Long.class), any(String.class));
  }

  @Test
  @DisplayName("이메일별 전체 주문 내역 조회 API 테스트")
  void get_order_list_test() throws Exception {

    OrderListResponse orderListResponse1 = new OrderListResponse(
        "test@example.com",
        1L,
        Status.BEFORE_SHIPPING,
        LocalDate.now(),
        Collections.emptyList()
    );

    OrderListResponse orderListResponse2 = new OrderListResponse(
        "test@example.com",
        2L,
        Status.SHIPPING,
        LocalDate.now(),
        Collections.emptyList()
    );

    when(orderService.getOrdersByEmail(any(String.class)))
        .thenReturn(Arrays.asList(orderListResponse1, orderListResponse2));

    mockMvc.perform(get("/api/order")
            .param("email", orderRequest.getEmail()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())  // 결과가 배열 형태인지 확인
        .andExpect(jsonPath("$[0].email").value(orderRequest.getEmail()))
        .andExpect(jsonPath("$[0].status").value("BEFORE_SHIPPING"))
        .andExpect(jsonPath("$[1].email").value(orderRequest.getEmail()))
        .andExpect(jsonPath("$[1].status").value("SHIPPING"));

//        assertThat(orderListResponse1.getEmail()).isEqualTo(orderRequest.getEmail());
//        assertThat(orderListResponse2.getStatus()).isEqualTo(Status.SHIPPING);
  }

  @Test
  @DisplayName("개별 주문 내역 조회 API 테스트")
  void get_order_detail_test() throws Exception {

    when(orderService.getOrderByIdAndEmail(any(Long.class), any(String.class))).thenReturn(orderDetailResponse);

    mockMvc.perform(get("/api/order/{orderId}", 1L)
            .param("email", orderRequest.getEmail()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.address").value("addressTest"))
        .andExpect(jsonPath("$.postcode").value("12345"))
        .andExpect(jsonPath("$.status").value("BEFORE_SHIPPING"))
        .andExpect(jsonPath("$.createdAt").exists());
  }
}