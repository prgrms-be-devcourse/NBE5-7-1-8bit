package programmers.nbe5_7_1_8bit.domain.product.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductRequestDto;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductResponseDto;
import programmers.nbe5_7_1_8bit.domain.product.entity.Product;
import programmers.nbe5_7_1_8bit.domain.product.exception.ProductException.ProductNotFoundException;
import programmers.nbe5_7_1_8bit.domain.product.exception.ProductException.RemovedProductException;
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
    ProductResponseDto product = productService.createProduct(request);

    //then
    Product saved = productRepository.findById(product.getId()).orElseThrow();
    assertThat(saved.getName()).isEqualTo("아메리카노");
    assertThat(saved.getPrice()).isEqualTo(1000);
    assertThat(saved.getStock()).isEqualTo(10);

  }

  @Test
  @DisplayName("제품 조회 테스트")
  void 제품_조회_성공() throws Exception {
    //given
    ProductRequestDto request = ProductRequestDto.builder()
        .name("라떼")
        .price(2000)
        .stock(20)
        .build();

    ProductResponseDto createdProduct = productService.createProduct(request);

    //when
    ProductResponseDto retrievedProduct = productService.getProduct(createdProduct.getId());

    //then
    assertThat(retrievedProduct.getId()).isEqualTo(createdProduct.getId());
    assertThat(retrievedProduct.getName()).isEqualTo("라떼");
    assertThat(retrievedProduct.getPrice()).isEqualTo(2000);
    assertThat(retrievedProduct.getStock()).isEqualTo(20);
    
  }
  
  @Test
  @DisplayName("제품 수정 테스트")
  void 제품_수정_성공() throws Exception {
    //given
    ProductRequestDto request = ProductRequestDto.builder()
        .name("아이스티")
        .price(1500)
        .stock(15)
        .build();

    ProductResponseDto createdProduct = productService.createProduct(request);

    ProductRequestDto updateRequest = ProductRequestDto.builder()
        .name("아이스티티")
        .price(15000)
        .stock(155)
        .build();

    //when
    ProductResponseDto updatedProduct = productService.updateProduct(createdProduct.getId(), updateRequest);

    //then
    assertThat(updatedProduct.getName()).isEqualTo("아이스티티");
    assertThat(updatedProduct.getPrice()).isEqualTo(15000);
    assertThat(updatedProduct.getStock()).isEqualTo(155);


  }

  @Test
  @DisplayName("제품 삭제 테스트")
  void 제품_삭제_성공() throws Exception {
    //given
    ProductRequestDto request = ProductRequestDto.builder()
        .name("핫초코")
        .price(1500)
        .stock(17)
        .build();

    ProductResponseDto createdProduct = productService.createProduct(request);

    //when
    productService.deleteProduct(createdProduct.getId());

    //then
    assertThrows(IllegalStateException.class, () -> productService.getProduct(createdProduct.getId()));
    assertThrows(IllegalArgumentException.class, () -> productService.getProduct(createdProduct.getId()));

  }

//  @Test
//  @DisplayName("제품 목록 조회 테스트")
//  void 제품_목록_조회_성공() throws Exception {
//    //given
//    ProductRequestDto.builder()
//        .name("1")
//        .price(11)
//        .stock(111)
//        .build();
//
//    ProductRequestDto.builder()
//        .name("2")
//        .price(22)
//        .stock(222)
//        .build();
//
//    //when
//    List<ProductResponseDto> products = productService.getAllProduct();
//
//    //then
//    assertThat(products.size()).isEqualTo(2);
//    assertThat(products.get(0).getName()).isEqualTo("1");
//    assertThat(products.get(1).getName()).isEqualTo("2");
//
//
//
//
//
//  }
  
  
  
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
