package programmers.nbe5_7_1_8bit.domain.order_product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmers.nbe5_7_1_8bit.domain.order_product.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
