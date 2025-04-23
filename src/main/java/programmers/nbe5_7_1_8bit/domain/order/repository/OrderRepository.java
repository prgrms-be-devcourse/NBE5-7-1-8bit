package programmers.nbe5_7_1_8bit.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmers.nbe5_7_1_8bit.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
