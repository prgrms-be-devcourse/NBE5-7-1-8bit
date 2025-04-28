package programmers.nbe5_7_1_8bit.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("카페 메뉴 관리 API")
            .version("1.0")
            .description("카페 메뉴 및 주문 관리를 위한 API 명세서입니다."));
  }
}
