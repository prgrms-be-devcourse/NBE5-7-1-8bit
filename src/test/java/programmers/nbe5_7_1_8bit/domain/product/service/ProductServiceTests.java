package programmers.nbe5_7_1_8bit.domain.product.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductRequestDto;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductResponseDto;
import programmers.nbe5_7_1_8bit.domain.product.entity.Product;
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
    ProductResponseDto updatedProduct = productService.updateProduct(createdProduct.getId(),
        updateRequest);

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
    assertThrows(RemovedProductException.class,
        () -> productService.getProduct(createdProduct.getId()));
  }


}
