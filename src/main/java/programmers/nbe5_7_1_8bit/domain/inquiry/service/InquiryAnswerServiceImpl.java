package programmers.nbe5_7_1_8bit.domain.inquiry.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryAnswerDto;
import programmers.nbe5_7_1_8bit.domain.inquiry.repository.InquiryAnswerRepository;
import programmers.nbe5_7_1_8bit.domain.inquiry.repository.InquiryRepository;
import programmers.nbe5_7_1_8bit.global.exception.IllegalInquiryIdException;

@Service
@RequiredArgsConstructor
@Transactional
public class InquiryAnswerServiceImpl implements InquiryAnswerService {

  private final InquiryAnswerRepository inquiryAnswerRepository;
  private final InquiryRepository inquiryRepository;

  @Override
  public void save(InquiryAnswerDto inquiryAnswerDto) {
    inquiryRepository
        .findById(inquiryAnswerDto.getInquiryId())
        .ifPresentOrElse(
            inquiry -> inquiry.answerInquiry(InquiryAnswerDto.of(inquiryAnswerDto.getAnswer())),
            () -> {
              throw new IllegalInquiryIdException();
            });
  }

  @Override
  public InquiryAnswerDto findById(Long id) {
    return inquiryRepository.findInquiryAnswerDtoById(id);
  }

  @Override
  public void update(InquiryAnswerDto inquiryAnswerDto) {
    inquiryAnswerRepository
        .findById(inquiryAnswerDto.getInquiryId())
        .ifPresent(inquiryAnswer -> inquiryAnswer.update(inquiryAnswerDto));
  }

  @Override
  public void softRemove(Long id) {
    inquiryAnswerRepository.findById(id).ifPresent(inquiryAnswer -> inquiryAnswer.softDelete());
  }
}
