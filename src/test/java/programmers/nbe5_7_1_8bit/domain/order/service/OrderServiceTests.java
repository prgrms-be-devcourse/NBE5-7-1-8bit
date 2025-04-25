package programmers.nbe5_7_1_8bit.domain.order.service;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programmers.nbe5_7_1_8bit.domain.member.entity.Member;
import programmers.nbe5_7_1_8bit.domain.member.repository.MemberRepository;
import programmers.nbe5_7_1_8bit.domain.order.dto.request.OrderRequest;
import programmers.nbe5_7_1_8bit.domain.order.dto.response.OrderDetailResponse;
import programmers.nbe5_7_1_8bit.domain.order.dto.response.OrderListResponse;
import programmers.nbe5_7_1_8bit.domain.order.entity.Order;
import programmers.nbe5_7_1_8bit.domain.order.entity.Status;
import programmers.nbe5_7_1_8bit.domain.order.repository.OrderRepository;
import programmers.nbe5_7_1_8bit.domain.order_product.dto.request.OrderProductRequest;
import programmers.nbe5_7_1_8bit.domain.order_product.entity.OrderProduct;
import programmers.nbe5_7_1_8bit.domain.order_product.repository.OrderProductRepository;
import programmers.nbe5_7_1_8bit.domain.product.entity.Product;
import programmers.nbe5_7_1_8bit.domain.product.repository.ProductRepository;

//@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private OrderProductRepository orderProductRepository;

  @InjectMocks
  private OrderService orderService;


  @Test
  @DisplayName("상품 주문 테스트")
  void orderTest() throws Exception {
    // given
    Member member = new Member("123@mail");

    when(memberRepository.findByEmail("123@mail"))
        .thenReturn(Optional.empty());
    when(memberRepository.save(any(Member.class)))
        .thenReturn(member);

    // 임시 상품 생성
    Product product = Product.builder()
        .name("커피")
        .price(3000)
        .stock(10)
        .build();

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    OrderRequest request = new OrderRequest(
        "123@mail",
        "서울시 강남구",
        "12345",
        of(new OrderProductRequest(1L, 2)) // 2개 주문
    );

    // when
    orderService.save(request);

    // then
    verify(memberRepository, times(1)).save(any(Member.class));
    verify(orderRepository, times(1)).save(any(Order.class));
    verify(productRepository, times(1)).findById(1L);
    verify(orderProductRepository, times(1)).save(any(OrderProduct.class));

  }

  @Test
  @DisplayName("존재하지 않는 상품 주문 시 예외 발생")
  void noExistProductOrder() {
    // given
    Member member = Member.builder()
        .email("noexist@email.com")
        .build();

    OrderRequest request = new OrderRequest(
        "noexist@email.com", "서울", "12345",
        List.of(new OrderProductRequest(999L, 1)) // 존재하지 않는 상품 ID
    );

    when(memberRepository.findByEmail("noexist@email.com"))
        .thenReturn(Optional.of(member));
    when(productRepository.findById(999L))
        .thenReturn(Optional.empty()); // 상품 없음

    // when & then
    assertThrows(IllegalArgumentException.class, () -> {
      orderService.save(request);
    });

    verify(productRepository, times(1)).findById(999L);
  }

  @Test
  @DisplayName("상품 주문 후 product 수량 차감 test")
  void productDecreaseTest() throws Exception {
    // given
    Member member = Member.builder()
        .email("123@email.com")
        .build();
    Product product = Product.builder()
        .name("콜드브루")
        .price(3000)
        .stock(10)
        .build();

    OrderRequest request = new OrderRequest(
        "123@email.com", "서울", "12345",
        List.of(new OrderProductRequest(1L, 3))
    );

    when(memberRepository.findByEmail("123@email.com"))
        .thenReturn(Optional.of(member));
    when(productRepository.findById(1L))
        .thenReturn(Optional.of(product));
    when(orderRepository.save(any(Order.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
    when(orderProductRepository.save(any(OrderProduct.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // when
    orderService.save(request);

    // then
    Assertions.assertThat(product.getStock()).isEqualTo(7);
    verify(productRepository, times(1)).findById(1L);
    verify(orderProductRepository, times(1)).save(any(OrderProduct.class));
  }

  @Test
  @DisplayName("주문 수정 성공 테스트")
  void updateOrderTestSuccess() throws Exception {

    // given
    Member member = Member.builder()
        .email("user@mail.com")
        .build();

    Order order = Order.builder()
        .member(member)
        .address("old")
        .postcode("old")
        .status(Status.BEFORE_SHIPPING)
        .build();

    Product product = Product.builder()
        .name("테스트 상품")
        .price(10000)
        .stock(10)
        .build();

    OrderProduct oldOrderProduct = OrderProduct.builder()
        .product(product)
        .quantity(10)
        .build();

    product.decreaseStock(oldOrderProduct.getQuantity());
    oldOrderProduct.setOrder(order);

    OrderProductRequest newOrderProductRequest =
        new OrderProductRequest(1L, 2);

    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(orderProductRepository.findOrderProductByOrder(order)).thenReturn(of(oldOrderProduct));
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    // when
    orderService.updateOrder(1L, new OrderRequest(member.getEmail(), "newAddress", "newPostcode",
        of(newOrderProductRequest)));

    // then
    verify(orderProductRepository, times(1)).findOrderProductByOrder(order);
    verify(orderProductRepository, times(1)).delete(oldOrderProduct);
    verify(orderProductRepository, times(1)).save(any(OrderProduct.class));
    assertThat(product.getStock()).isEqualTo(8);

  }

  @Test
  @DisplayName("주문 수정 실패 테스트(이메일 불일치)")
  void updateOrderTestFail() throws Exception {

    // given
    Order order = mock(Order.class);
    when(order.isOwnedBy("wrong@mail.com")).thenReturn(false);
    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

    // when then
    assertThatThrownBy(
        () -> {
          orderService.updateOrder(1L,
              new OrderRequest("wrong@mail.com", "newAddress", "newPostcode", of()));
        }
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("자신의 주문만 수정 가능합니다.");


  }

  @Test
  @DisplayName("주문 취소 성공 테스트")
  void cancelTestSuccess() throws Exception {

    // given
    Member member = Member.builder()
        .email("user@mail.com")
        .build();

    Order order = Order.builder()
        .member(member)
        .address("address")
        .postcode("postcode")
        .status(Status.BEFORE_SHIPPING)
        .build();

    Product product = Product.builder()
        .name("테스트 상품")
        .price(10000)
        .stock(10)
        .build();

    OrderProduct orderProduct = OrderProduct.builder()
        .product(product)
        .quantity(5)
        .build();

    orderProduct.setOrder(order);

    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(orderProductRepository.findOrderProductByOrder(order)).thenReturn(of(orderProduct));

    // when
    orderService.cancelOrder(1L, member.getEmail());

    // then
    assertThat(order.getStatus()).isEqualTo(Status.CANCELED);
  }

  @Test
  @DisplayName("주문 취소 테스트 실패(주문 내역 없음)")
  void cancelOrderTestFail() throws Exception {

    // given
    when(orderRepository.findById(1L)).thenReturn(Optional.empty());

    // when, then
    assertThatThrownBy(
        () -> {
          orderService.cancelOrder(1L, "user@mail.com");
        }
    ).isInstanceOf(NoSuchElementException.class)
        .hasMessageContaining("주문이 존재하지 않습니다.");
  }


  @Test
  @DisplayName("주문 내역 조회 성공 테스트")
  void getOrdersByEmailTestSuccess() throws Exception {

    // given
    Member member = Member.builder()
        .email("user@mail.com")
        .build();

    Order order = Order.builder()
        .member(member)
        .address("address")
        .postcode("postcode")
        .status(Status.BEFORE_SHIPPING)
        .build();

    when(orderRepository.findByMember_Email(member.getEmail())).thenReturn(List.of(order));
    when(orderProductRepository.findOrderProductByOrder(order)).thenReturn(List.of());

    // when
    List<OrderListResponse> ordersByEmail = orderService.getOrdersByEmail(member.getEmail());

    // then
    Assertions.assertThat(ordersByEmail).hasSize(1);
    Assertions.assertThat(ordersByEmail.get(0).getEmail()).isEqualTo(member.getEmail());
    verify(orderRepository, times(1)).findByMember_Email(member.getEmail());
    verify(orderProductRepository, times(1)).findOrderProductByOrder(order);

  }

  @Test
  @DisplayName("주문 내역 조회 실패 테스트(주문 내역 없음)")
  void getOrdersByEmailTestFail() throws Exception {

    // given
    String email = "user@mail.com";
    when(orderRepository.findByMember_Email(email)).thenReturn(List.of());

    // when then
    assertThatThrownBy(
        () -> {
          orderService.getOrdersByEmail(email);
        }
    ).isInstanceOf(NoSuchElementException.class)
        .hasMessageContaining("주문 내역이 없습니다.");
  }

  @Test
  @DisplayName("주문 내역 상세 조회 성공 테스트")
  void getOrderByIdAndEmailTestSuccess() throws Exception {

    // given
    Member member = Member.builder()
        .email("user@mail.com")
        .build();

    Order order = Order.builder()
        .member(member)
        .address("address")
        .postcode("postcode")
        .status(Status.BEFORE_SHIPPING)
        .build();

    when(orderRepository.findByIdAndMember_Email(1L, member.getEmail())).thenReturn(
        Optional.of(order));
    when(orderProductRepository.findOrderProductByOrder(order)).thenReturn(List.of());

    // when
    OrderDetailResponse orderByIdAndEmail = orderService.getOrderByIdAndEmail(1L,
        member.getEmail());

    // then
    Assertions.assertThat(orderByIdAndEmail.getAddress()).isEqualTo(order.getAddress());
    Assertions.assertThat(orderByIdAndEmail.getPostcode()).isEqualTo(order.getPostcode());
    assertThat(orderByIdAndEmail.getStatus()).isEqualTo(Status.BEFORE_SHIPPING);
    verify(orderRepository, times(1)).findByIdAndMember_Email(1L, member.getEmail());
    verify(orderProductRepository, times(1)).findOrderProductByOrder(order);
  }

  @Test
  @DisplayName("주문 내역 상세 조회 실패 테스트(해당하는 id의 주문 내역 없음)")
  void getOrderByIdAndEmailTestFail() throws Exception {

    // given
    when(orderRepository.findByIdAndMember_Email(0L, "user@mail.com")).thenReturn(Optional.empty());

    // when then
    assertThatThrownBy(
        () -> {
          orderService.getOrderByIdAndEmail(0L, "user@mail.com");
        }
    ).isInstanceOf(RuntimeException.class)
        .hasMessageContaining("해당하는 주문 내역이 없습니다.");

  }

}