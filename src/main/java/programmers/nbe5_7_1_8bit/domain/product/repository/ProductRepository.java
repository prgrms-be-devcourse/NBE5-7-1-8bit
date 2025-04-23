package programmers.nbe5_7_1_8bit.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmers.nbe5_7_1_8bit.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
