package programmers.nbe5_7_1_8bit.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ProductRequestDto {
  private Long id;
  private String name;
  private int price;
  private int stock;

}
