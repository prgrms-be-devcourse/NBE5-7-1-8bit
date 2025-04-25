package programmers.nbe5_7_1_8bit.domain.product.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductRequestDto;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductResponseDto;
import programmers.nbe5_7_1_8bit.domain.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto request) {
    ProductResponseDto product = productService.createProduct(request);
    return ResponseEntity.status(201).body(product);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long productId) {
    ProductResponseDto product = productService.getProduct(productId);
    return ResponseEntity.ok(product);
  }

  @GetMapping("/member/{productId}")
  public ResponseEntity<ProductResponseDto> memberGetProduct(@PathVariable Long productId){
    ProductResponseDto product = productService.memberGetProduct(productId);
    return ResponseEntity.ok(product);
  }

  @GetMapping("/list")
  public ResponseEntity<List<ProductResponseDto>> memberGetProductList() {
    List<ProductResponseDto> productList = productService.memberGetProductList();
    return ResponseEntity.ok(productList);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ProductResponseDto> updateProduct (@PathVariable Long productId, @RequestBody ProductRequestDto updateRequest) {
    ProductResponseDto updatedProduct = productService.updateProduct(productId, updateRequest);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{productId}/image")
  public ResponseEntity<String> uploadImage(@PathVariable Long productId, @RequestParam("file")MultipartFile file)
      throws IOException {
    String fileName = productService.uploadImage(productId, file);
    return ResponseEntity.ok(fileName);
  }

  @GetMapping("/{productId}/image")
  public ResponseEntity<Resource> loadImage(@PathVariable Long productId) throws IOException {
    Resource resource = productService.loadImage(productId);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
        .contentType(MediaType.IMAGE_JPEG) 
        .body(resource);
  }

  @GetMapping("/{productId}/image")
  public ResponseEntity<Resource> loadImage(@PathVariable Long productId) throws IOException {
    Resource resource = productService.loadImage(productId);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
        .contentType(MediaType.IMAGE_JPEG)
        .body(resource);
  }


}
