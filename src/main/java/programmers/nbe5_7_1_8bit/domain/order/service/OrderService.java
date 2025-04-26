package programmers.nbe5_7_1_8bit.domain.order.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;
  private final OrderProductRepository orderProductRepository;

  public void save(OrderRequest orderRequest) {

    Member member = memberRepository.findByEmail(orderRequest.getEmail())
        .orElseGet(() -> {
              Member newMember = Member.builder()
                  .email(orderRequest.getEmail())
                  .build();
              return memberRepository.save(newMember);
            }
        );

    Order order = Order.builder()
        .member(member)
        .address(orderRequest.getAddress())
        .postcode(orderRequest.getPostcode())
        .status(Status.BEFORE_SHIPPING)
        .build();

    orderRepository.save(order);

    for (OrderProductRequest orderProduct : orderRequest.getOrderProducts()) {

      Product product = productRepository.findById(orderProduct.getProductId())
          .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

      product.decreaseStock(orderProduct.getQuantity());

      OrderProduct newOrderProduct = OrderProduct.builder()
          .product(product)
          .quantity(orderProduct.getQuantity())
          .build();

      newOrderProduct.setOrder(order);

      orderProductRepository.save(newOrderProduct);

    }

  }

  public void updateOrder(Long orderId, OrderRequest request) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NoSuchElementException("주문이 존재하지 않습니다."));

    if (!order.isOwnedBy(request.getEmail())) {
      throw new IllegalArgumentException("자신의 주문만 수정 가능합니다.");
    }

    //취소된 상품의 재고를 다시 채우는 기능
    List<OrderProduct> oldProducts = orderProductRepository.findOrderProductByOrder(order);

    for (OrderProduct oldProduct : oldProducts) {
      Product product = oldProduct.getProduct();
      product.increaseStock(oldProduct.getQuantity());
      orderProductRepository.delete(oldProduct);
    }

    //새로운 주문 상품의 재고를 줄이는 기능 필요
    List<OrderProduct> newProducts = toOrderProducts(order, request.getOrderProducts());

    for (OrderProduct newProduct : newProducts) {
      Product product = newProduct.getProduct();
      product.decreaseStock(newProduct.getQuantity()); //decreaseStock에서 재고가 부족한경우 예외를 반환
      orderProductRepository.save(newProduct);
    }
    order.updateAddress(request.getAddress(), request.getPostcode());


  }

  public void adminUpdateOrder(Long orderId, OrderRequest request) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NoSuchElementException("주문이 존재하지 않습니다."));

    //취소된 상품의 재고를 다시 채우는 기능 필요
    List<OrderProduct> oldProducts = orderProductRepository.findOrderProductByOrder(order);

    for (OrderProduct oldProduct : oldProducts) {
      Product product = oldProduct.getProduct();
      product.increaseStock(oldProduct.getQuantity());
      orderProductRepository.delete(oldProduct);
    }

    //새로운 주문 상품의 재고를 줄이는 기능 필요
    List<OrderProduct> newProducts = toOrderProducts(order, request.getOrderProducts());

    for (OrderProduct newProduct : newProducts) {
      Product product = newProduct.getProduct();
      product.decreaseStock(newProduct.getQuantity()); //decreaseStock에서 재고가 부족한경우 예외를 반환
      orderProductRepository.save(newProduct);
    }
    order.updateAddress(request.getAddress(), request.getPostcode());


  }

  public void cancelOrder(Long orderId, String memberEmail) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NoSuchElementException("주문이 존재하지 않습니다."));

    if (!order.isOwnedBy(memberEmail)) {
      throw new IllegalArgumentException("자신의 주문만 취소 가능합니다.");
    }

    //취소된 상품의 재고를 다시 채우는 기능
    List<OrderProduct> oldProducts = orderProductRepository.findOrderProductByOrder(order);

    for (OrderProduct oldProduct : oldProducts) {
      Product product = oldProduct.getProduct();
      product.increaseStock(oldProduct.getQuantity());
    }

    order.cancelOrder();

  }

  public void adminCancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NoSuchElementException("주문이 존재하지 않습니다."));

    //취소된 상품의 재고를 다시 채우는 기능
    List<OrderProduct> oldProducts = orderProductRepository.findOrderProductByOrder(order);

    for (OrderProduct oldProduct : oldProducts) {
      Product product = oldProduct.getProduct();
      product.increaseStock(oldProduct.getQuantity());
    }

    order.cancelOrder();

  }

  public List<OrderListResponse> getOrdersByEmail(String email) {
    List<Order> orders = orderRepository.findByMember_Email(email);

    if (orders.isEmpty()) {
      throw new NoSuchElementException("주문 내역이 없습니다.");
    }

    return orders.stream()
        .map(order -> OrderListResponse.from(order, orderProductRepository))
        .collect(Collectors.toList());
  }

  public OrderDetailResponse getOrderByIdAndEmail(Long id, String email) {
    Optional<Order> orderOptional = orderRepository.findByIdAndMember_Email(id, email);

    if (orderOptional.isPresent()) {
      Order order = orderOptional.get();
      return OrderDetailResponse.from(order, orderProductRepository);
    } else {
      throw new RuntimeException("해당하는 주문 내역이 없습니다.");
    }
  }


  private List<OrderProduct> toOrderProducts(Order order, List<OrderProductRequest> requests) {
    List<OrderProduct> orderProducts = new ArrayList<>();

    for (OrderProductRequest req : requests) {
      Product product = productRepository.findById(req.getProductId())
          .orElseThrow(
              () -> new NoSuchElementException("Product not found. ID: " + req.getProductId()));

      OrderProduct orderProduct = OrderProduct.builder()
          .quantity(req.getQuantity())
          .product(product)
          .build();

      orderProduct.setOrder(order);

      orderProducts.add(orderProduct);
    }

    return orderProducts;
  }

  public List<OrderListResponse> getAllOrders() {
    List<Order> orders = orderRepository.findAll();
    return orders.stream()
        .map(order -> OrderListResponse.from(order, orderProductRepository))
        .collect(Collectors.toList());
  }

  public OrderDetailResponse getOrderDetailById(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NoSuchElementException("주문 없음"));
    return OrderDetailResponse.from(order, orderProductRepository);
  }
}
