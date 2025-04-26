package programmers.nbe5_7_1_8bit.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import programmers.nbe5_7_1_8bit.domain.inquiry.repository.InquiryAnswerRepository;
import programmers.nbe5_7_1_8bit.domain.inquiry.repository.InquiryRepository;
import programmers.nbe5_7_1_8bit.domain.inquiry.service.InquiryAnswerService;
import programmers.nbe5_7_1_8bit.domain.inquiry.service.InquiryAnswerServiceImpl;
import programmers.nbe5_7_1_8bit.domain.inquiry.service.InquiryService;
import programmers.nbe5_7_1_8bit.domain.inquiry.service.InquiryServiceImpl;
import programmers.nbe5_7_1_8bit.domain.manager.repository.ManagerRepository;
import programmers.nbe5_7_1_8bit.domain.manager.service.AuthService;
import programmers.nbe5_7_1_8bit.domain.manager.service.AuthServiceImpl;

@EnableJpaAuditing
@Configuration
@RequiredArgsConstructor
@EnableAspectJAutoProxy
public class CustomConfiguration {

  private final InquiryRepository inquiryRepository;
  private final InquiryAnswerRepository inquiryAnswerRepository;
  private final ManagerRepository managerRepository;
  private final HibernateFilterManager hibernateFilterManager;

  @Bean
  InquiryService inquiryService() {
    return new InquiryServiceImpl(hibernateFilterManager, inquiryRepository);
  }

  @Bean
  InquiryAnswerService inquiryAnswerService() {
    return new InquiryAnswerServiceImpl(inquiryAnswerRepository, inquiryRepository);
  }

  @Bean
  AuthService authService() {
    return new AuthServiceImpl(managerRepository);
  }
}
