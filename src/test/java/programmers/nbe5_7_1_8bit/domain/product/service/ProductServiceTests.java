package programmers.nbe5_7_1_8bit.domain.product.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductRequestDto;
import programmers.nbe5_7_1_8bit.domain.product.entity.Product;
import programmers.nbe5_7_1_8bit.domain.product.repository.ProductRepository;

@SpringBootTest
class ProductServiceTests {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductRepository productRepository;

  @Test
  @DisplayName("제품 등록 테스트")
  void 제품_등록_성공() throws Exception {

    //given
    ProductRequestDto request = ProductRequestDto.builder()
        .name("아메리카노")
        .price(1000)
        .stock(10)
        .build();

    //when
    Long productId = productService.createProduct(request);

    //then
    Product saved = productRepository.findById(productId).orElseThrow();
    assertThat(saved.getName()).isEqualTo("아메리카노");
    assertThat(saved.getPrice()).isEqualTo(1000);
    assertThat(saved.getStock()).isEqualTo(10);

  }


  
//  @Test
//  @DisplayName("연관관계 비교")
//  void testOneToMany() throws Exception {
//    Query orderQuery = entityManager.createQuery("SELECT o FROM Order o");
//    orderQuery.getResultList();
//
//
//    Query orderProductQuery = entityManager.createQuery("SELECT op FROM OrderProduct op");
//    orderProductQuery.getResultList();
//
//  }
//
//  @Test
//  @DisplayName("연관관계 성능 비교")
//  void testManyToOne() throws Exception {
//    Query orderProductQuery = entityManager.createQuery("SELECT op FROM OrderProduct op JOIN FETCH op.order");
//    orderProductQuery.getResultList();
//
//  }

}
