package programmers.nbe5_7_1_8bit.domain.manager.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import programmers.nbe5_7_1_8bit.domain.manager.entity.AdminAspect;
import programmers.nbe5_7_1_8bit.domain.manager.utils.CookieUtils;
import programmers.nbe5_7_1_8bit.domain.manager.utils.SessionUtils;

@TestPropertySource(locations = "classpath:application-test.yml")
@TestConfiguration
@EnableAspectJAutoProxy
@Import({SessionUtils.class, CookieUtils.class})
public class ManagerControlerTestConfig {

  @Bean
  public AdminAspect adminAspect(SessionUtils sessionUtils) {
    return new AdminAspect(sessionUtils);
  }
}
