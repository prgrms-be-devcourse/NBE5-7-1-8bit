package programmers.nbe5_7_1_8bit.domain.inquiry.repository;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.Inquiry;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryAnswer;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryAnswerDto;
import programmers.nbe5_7_1_8bit.domain.member.entity.Member;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class InquiryAnswerRepositoryTest {

  @Autowired
  private InquiryRepository inquiryRepository;
  @Autowired
  private InquiryAnswerRepository inquiryAnswerRepository;

  @Test
  void findInquiryAnswerDtoById() {
    // given
    Member member = new Member("m");
    List<Inquiry> inquiries = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Inquiry inquiry = new Inquiry("t", "q", "p", member);
      inquiry.answerInquiry(new InquiryAnswer("a" + i));
      inquiries.add(inquiry);
    }
    inquiryRepository.saveAll(inquiries);

    // when
    InquiryAnswerDto result = inquiryRepository.findInquiryAnswerDtoById(inquiries.get(0).getId());

    // then
    Assertions.assertThat(result.getAnswer()).isEqualTo("a0");
  }
}
