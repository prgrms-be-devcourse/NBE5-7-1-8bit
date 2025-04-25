package programmers.nbe5_7_1_8bit.domain.inquiry.service;

import org.springframework.data.domain.Page;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto;

public interface InquiryService {

  void save(InquiryDto inquiryDto);

  InquiryDto findById(Long inquiryId);

  Page<InquiryDto> findPage(int page, int offset);

  void update(InquiryDto inquiryDto);

  void softDelete(Long inquiryId);
}
