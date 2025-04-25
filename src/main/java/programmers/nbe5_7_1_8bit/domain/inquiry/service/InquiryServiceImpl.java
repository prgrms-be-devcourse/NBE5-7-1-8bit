package programmers.nbe5_7_1_8bit.domain.inquiry.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.Inquiry;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto;
import programmers.nbe5_7_1_8bit.domain.inquiry.repository.InquiryRepository;
import programmers.nbe5_7_1_8bit.domain.inquiry.utils.PasswordUtils;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InquiryServiceImpl implements InquiryService {

  private final InquiryRepository inquiryRepository;

  @Override
  public void save(InquiryDto inquiryDto) {
    inquiryRepository.save(
        InquiryDto.of(inquiryDto.getTitle(), inquiryDto.getQuestion(), inquiryDto.getName(),inquiryDto.getPassword()));
  }

  @Override
  public InquiryDto findById(Long inquiryId) {
    return inquiryRepository.findInquiryDtoById(inquiryId);
  }

  @Override
  public Page<InquiryDto> findPage(int page, int offset) {
    return inquiryRepository.findPageForManager(PageRequest.of(page, offset));
  }

  @Override
  public void update(InquiryDto inquiryDto) {
    inquiryRepository
        .findById(inquiryDto.getInquiryId())
        .ifPresent(inquiry -> inquiry.update(inquiryDto));
  }

  @Override
  public void softDelete(Long inquiryId) {
    inquiryRepository.findById(inquiryId).ifPresent(inquiry -> inquiry.softDelete());
  }

  @Override
  public boolean checkInquiryPassword(Long inquiryId, String password) {
    Optional<Inquiry> inquiry = inquiryRepository.findById(inquiryId);
    return inquiry.isPresent() && PasswordUtils.checkPw(inquiry.get().getPassword(), password);
  }
}
