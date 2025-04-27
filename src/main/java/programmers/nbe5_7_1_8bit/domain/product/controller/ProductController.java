package programmers.nbe5_7_1_8bit.domain.product.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import programmers.nbe5_7_1_8bit.domain.manager.entity.AdminOnly;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductRequestDto;
import programmers.nbe5_7_1_8bit.domain.product.dto.ProductResponseDto;
import programmers.nbe5_7_1_8bit.domain.product.service.ProductService;

@Controller
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  @AdminOnly
  public String listProducts(Model model) {
    List<ProductResponseDto> products = productService.memberGetProductList();
    model.addAttribute("products", products);
    return "admin/products/list";
  }

  @GetMapping("/{productId}/detail")
  @AdminOnly
  public String getProduct(@PathVariable Long productId, Model model) {
    ProductResponseDto product = productService.memberGetProduct(productId);
    model.addAttribute("product", product);
    return "admin/products/detail";
  }

  @GetMapping("/new")
  @AdminOnly
  public String createProductForm(Model model) {
    model.addAttribute("product", new ProductRequestDto());
    return "admin/products/form";
  }

  @PostMapping
  @AdminOnly
  public String createProduct(
      @ModelAttribute ProductRequestDto request,
      @RequestParam(value = "file", required = false) MultipartFile file,
      RedirectAttributes redirectAttributes) throws IOException {

    ProductResponseDto product = productService.createProduct(request);

    if (file != null && !file.isEmpty()) {
      productService.uploadImage(product.getId(), file);
    }

    redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 등록되었습니다.");
    return "redirect:/api/products";
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long productId) {
    ProductResponseDto product = productService.getProduct(productId);
    return ResponseEntity.ok(product);
  }

  @GetMapping("/{productId}/edit")
  @AdminOnly
  public String editProductForm(@PathVariable Long productId, Model model) {
    ProductResponseDto product = productService.getProduct(productId);
    model.addAttribute("product", product);
    return "admin/products/form";
  }

  @ResponseBody
  @GetMapping("/member/{productId}")
  public ResponseEntity<ProductResponseDto> memberGetProduct(@PathVariable Long productId) {
    ProductResponseDto product = productService.memberGetProduct(productId);
    return ResponseEntity.ok(product);
  }

  @ResponseBody
  @GetMapping("/list")
  public ResponseEntity<List<ProductResponseDto>> memberGetProductList() {
    List<ProductResponseDto> productList = productService.memberGetProductList();
    return ResponseEntity.ok(productList);
  }


  @PostMapping("/{productId}/edit")
  @AdminOnly
  public String editProduct(@PathVariable Long productId,
      @ModelAttribute ProductRequestDto updateRequest, RedirectAttributes redirectAttributes) {
    productService.editProduct(productId, updateRequest);
    redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다.");
    return "redirect:/api/products";
  }

  @PostMapping("/{productId}/delete")
  @AdminOnly
  public String deleteProduct(@PathVariable Long productId, RedirectAttributes redirectAttributes) {
    productService.deleteProduct(productId);
    redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 삭제되었습니다.");
    return "redirect:/api/products";
  }

  @GetMapping("/{productId}/image/form")
  @AdminOnly
  public String imageUploadForm(@PathVariable Long productId, Model model) {
    ProductResponseDto product = productService.getProduct(productId);
    model.addAttribute("product", product);
    return "admin/products/image";
  }

  @PostMapping("/{productId}/image")
  @AdminOnly
  public String uploadImage(@PathVariable Long productId,
      @RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes) throws IOException {
    productService.uploadImage(productId, file);
    redirectAttributes.addFlashAttribute("message", "이미지가 성공적으로 업로드되었습니다.");
    return "redirect:/api/products/" + productId + "/edit";
  }

  @GetMapping("/{productId}/image")
  public ResponseEntity<Resource> loadImage(@PathVariable Long productId) throws IOException {
    Resource resource = productService.loadImage(productId);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "inline; filename=\"" + resource.getFilename() + "\"")
        .contentType(MediaType.IMAGE_JPEG)
        .body(resource);
  }

  @PostMapping("/{productId}/image/delete")
  @AdminOnly
  public String deleteImage(@PathVariable Long productId, RedirectAttributes redirectAttributes) throws IOException {
    productService.deleteImage(productId);
    redirectAttributes.addFlashAttribute("message", "이미지가 성공적으로 삭제되었습니다.");
    return "redirect:/api/products/" + productId + "/edit";
  }

}
