package programmers.nbe5_7_1_8bit.domain.order.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import programmers.nbe5_7_1_8bit.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByMember_Email(String email);

  Optional<Order> findByIdAndMember_Email(Long id, String email);

}
