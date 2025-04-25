package programmers.nbe5_7_1_8bit.domain.inquiry.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.Inquiry;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryAnswerDto;
import programmers.nbe5_7_1_8bit.domain.inquiry.repository.InquiryAnswerRepository;
import programmers.nbe5_7_1_8bit.domain.inquiry.repository.InquiryRepository;

@ExtendWith(MockitoExtension.class)
class InquiryAnswerServiceImplTest {
  @Mock private InquiryAnswerRepository inquiryAnswerRepository;
  @Mock private InquiryRepository inquiryRepository;
  @InjectMocks private InquiryAnswerServiceImpl inquiryAnswerService;
  @Test
  void should_save_when_answerInquiry() {
    // given
    Long inquiryId = 1L;
    String answer = "답변 내용입니다.";

    InquiryAnswerDto dto = new InquiryAnswerDto();
    dto.setId(inquiryId);
    dto.setAnswer(answer);

    Inquiry mockInquiry = Inquiry.builder().build();
    when(inquiryRepository.findById(inquiryId)).thenReturn(Optional.of(mockInquiry));

    // when
    inquiryAnswerService.save(dto);

    // then
    assertNotNull(mockInquiry.getInquiryAnswer()); // 실제 set 됐는지 확인
    assertEquals(answer, mockInquiry.getInquiryAnswer().getAnswer()); // 값이 제대로 들어갔는지
  }

  @Test
  void 답변_저장_대상_없음() {
    // given
    Long inquiryId = 999L;
    InquiryAnswerDto dto = new InquiryAnswerDto();
    dto.setId(inquiryId);
    dto.setAnswer("무의미한 답변");

    when(inquiryRepository.findById(inquiryId)).thenReturn(Optional.empty());

    // when / then
    assertDoesNotThrow(() -> inquiryAnswerService.save(dto));
    // 아무 동작도 하지 않음 (예외 없이 종료되면 성공)
  }
}
