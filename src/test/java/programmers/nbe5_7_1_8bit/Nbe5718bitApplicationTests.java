package programmers.nbe5_7_1_8bit;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.Inquiry;
import programmers.nbe5_7_1_8bit.domain.inquiry.repository.InquiryRepository;

@SpringBootTest
class Nbe5718bitApplicationTests {

  @Autowired
  private InquiryRepository inquiryRepository;

  @Test
  void contextLoads(@Autowired EntityManager entityManager) {
    for (int i = 0; i < 100; i++) {
      inquiryRepository.save(
          Inquiry.builder().title("t" + i).question("q" + i).name("n" + i).build());
    }
//    entityManager.flush();
  }
}
