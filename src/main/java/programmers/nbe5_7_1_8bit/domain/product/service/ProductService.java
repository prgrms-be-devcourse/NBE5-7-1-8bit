package programmers.nbe5_7_1_8bit.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductRequestDto;
import programmers.nbe5_7_1_8bit.domain.product.entity.Product;
import programmers.nbe5_7_1_8bit.domain.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

  @Autowired
  private final ProductRepository productRepository;


  public Long createProduct(ProductRequestDto request) {
    Product product = Product.builder()
        .name(request.getName())
        .price(request.getPrice())
        .stock(request.getStock())
        .build();

    return productRepository.save(product).getId();
  }

}
