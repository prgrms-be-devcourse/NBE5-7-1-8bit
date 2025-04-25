package programmers.nbe5_7_1_8bit.domain.inquiry.service;

import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryAnswerDto;

public interface InquiryAnswerService {

  void save(InquiryAnswerDto inquiryAnswerDto);

  InquiryAnswerDto findById(Long id);

  void update(InquiryAnswerDto inquiryAnswerDto);

  void softRemove(Long id);
}
