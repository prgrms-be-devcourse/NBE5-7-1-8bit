package programmers.nbe5_7_1_8bit.domain.inquiry.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.Inquiry;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto;
import programmers.nbe5_7_1_8bit.global.config.HibernateFilterManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(HibernateFilterManager.class)
class InquiryRepositoryTest {

  @Autowired
  private InquiryRepository inquiryRepository;

  @Test
  void findInquiryDtoById() {
    // given
    // when
    Inquiry savedInquiry = inquiryRepository.save(new Inquiry("t", "q", "p", "n"));

    // then
    assertThat(savedInquiry).isNotNull();
  }

  @Test
  void findPageForManager() {
    // given
    for (int i = 0; i < 10; i++) {
      inquiryRepository.save(new Inquiry("t", "q", "p", "n"));
    }

    // when
    Page<InquiryDto> result = inquiryRepository.findPageForManager(PageRequest.of(0, 2));

    // then
    assertThat(result).hasSize(2);
  }

  @Test
  void softDelete(@Autowired HibernateFilterManager hibernateFilterManager) {
    hibernateFilterManager.enableFilter("softDeleteFilter", "isRemoved", false);

    // given
    List<Inquiry> inquiries = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      inquiries.add(new Inquiry("t", "q", "p", "n"));
    }
    List<Inquiry> result = inquiryRepository.saveAll(inquiries);

    // when
    for (int i = 0; i < 3; i++) {
      result.get(i).softDelete();
    }
    inquiryRepository.saveAll(result);

    List<Inquiry> findInquires = inquiryRepository.findAllByName("n");
    // then
    hibernateFilterManager.disableFilter("softDeleteFilter");
    assertThat(findInquires).hasSize(7);
  }
}
