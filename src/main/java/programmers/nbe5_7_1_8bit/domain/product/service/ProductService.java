package programmers.nbe5_7_1_8bit.domain.product.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductRequestDto;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductResponseDto;
import programmers.nbe5_7_1_8bit.domain.product.entity.Product;
import programmers.nbe5_7_1_8bit.domain.product.exception.ProductException.ImageReadException;
import programmers.nbe5_7_1_8bit.domain.product.exception.ProductException.ProductImageNotFoundException;
import programmers.nbe5_7_1_8bit.domain.product.exception.ProductException.ProductNotFoundException;
import programmers.nbe5_7_1_8bit.domain.product.exception.ProductException.RemovedProductException;
import java.util.ArrayList;
import java.util.List;
import programmers.nbe5_7_1_8bit.domain.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  @Value("${file.upload-directory}")
  private String UPLOAD_DIRECTORY;

  @Transactional
  public ProductResponseDto createProduct(ProductRequestDto request) {

    Product product = Product.builder()
        .name(request.getName())
        .price(request.getPrice())
        .stock(request.getStock())
        .build();

    Product savedProduct = productRepository.save(product);
    return ProductResponseDto.from(savedProduct);
  }

  @Transactional(readOnly = true)
  public ProductResponseDto getProduct(Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(ProductNotFoundException::new);

    validateNotRemoved(product);

    return ProductResponseDto.from(product);
  }

  @Transactional
  public ProductResponseDto memberGetProduct(Long productId){
    Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException());

    validateNotRemoved(product);
    return ProductResponseDto.from(product);
  }

  @Transactional
  public ProductResponseDto editProduct(Long id, ProductRequestDto updateRequest) {
    Product product = productRepository.findById(id)
        .orElseThrow(ProductNotFoundException::new);

    validateNotRemoved(product);

    product.update(updateRequest.getName(), updateRequest.getPrice(), updateRequest.getStock());

    return ProductResponseDto.from(product);
  }

  @Transactional
  public void deleteProduct(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(ProductNotFoundException::new);
    product.softDelete();
  }

  @Transactional
  public String uploadImage(Long productId, MultipartFile file) throws IOException {
    Product product = productRepository.findById(productId)
        .orElseThrow(ProductNotFoundException::new);

    if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
      deleteImageFile(product.getImagePath());
    }

    String uploadDir = new File(UPLOAD_DIRECTORY).getAbsolutePath();
    Path uploadPath = Paths.get(uploadDir);

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    String originFileName = file.getOriginalFilename();
    if(originFileName == null || originFileName.isEmpty()) {
      throw new IllegalArgumentException("파일명이 유효하지 않습니다.");
    }
    String baseName = originFileName.substring(0, originFileName.lastIndexOf("."));
    String extension = originFileName.substring(originFileName.lastIndexOf("."));
    String savedFilename = baseName + "-" + UUID.randomUUID() + extension;

    Path filePath = uploadPath.resolve(savedFilename);
    file.transferTo(filePath);

    product.setImagePath(savedFilename);

    return savedFilename;
  }



  @Transactional(readOnly = true)
  public Resource loadImage(Long productId) throws IOException {
    Product product = productRepository.findById(productId)
        .orElseThrow(ProductNotFoundException::new);

    String imagePath = product.getImagePath();
    if(imagePath == null || imagePath.isEmpty()) {
      throw new ProductImageNotFoundException();
    }

    Path path = Paths.get(UPLOAD_DIRECTORY).resolve(imagePath);
    Resource resource = new UrlResource(path.toUri());

    if(!resource.exists() || !resource.isReadable()) {
      throw new ImageReadException(imagePath);
    }

    return resource;
  }

  @Transactional
  public void deleteImage(Long productId) throws IOException {
    Product product = productRepository.findById(productId)
        .orElseThrow(ProductNotFoundException::new);

    if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
      deleteImageFile(product.getImagePath());
      product.setImagePath(null);
    }
  }



  private void validateNotRemoved(Product product) {
    if(product.isRemoved()) {
      throw new RemovedProductException();
    }
  }

  public List<ProductResponseDto> memberGetProductList() {
    List<Product> productList = productRepository.findAll();

    List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

    for (Product product : productList) {
      if(!product.isRemoved()){
        productResponseDtoList.add(ProductResponseDto.from(product));
      }
    }

    return productResponseDtoList;
  }

  private void deleteImageFile(String imagePath) throws IOException {
    Path path = Paths.get(UPLOAD_DIRECTORY).resolve(imagePath);
    Files.deleteIfExists(path);
  }

}
