package programmers.nbe5_7_1_8bit.domain.product.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductRequestDto;
import programmers.nbe5_7_1_8bit.domain.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceUnitTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  @Test
  @DisplayName("제품 생성 테스트")
  void testCreateProduct() throws Exception {
    //given
    ProductRequestDto request = ProductRequestDto.builder()
        .name("아메리카노").price(2000).stock(100).build();




  }

}