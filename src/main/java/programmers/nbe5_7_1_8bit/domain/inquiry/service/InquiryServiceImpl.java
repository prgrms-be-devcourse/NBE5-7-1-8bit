package programmers.nbe5_7_1_8bit.domain.inquiry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto;
import programmers.nbe5_7_1_8bit.domain.inquiry.repository.InquiryRepository;
import programmers.nbe5_7_1_8bit.domain.member.entity.Member;
import programmers.nbe5_7_1_8bit.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InquiryServiceImpl implements InquiryService {

  private final InquiryRepository inquiryRepository;
  private final MemberRepository memberRepository;

  @Override
  public void save(InquiryDto inquiryDto) {
    String email = inquiryDto.getEmail();
    Member member =
        memberRepository
            .findByEmail(email)
            .orElseGet(() -> memberRepository.save(new Member(email)));

    inquiryRepository.save(InquiryDto.of(inquiryDto.getTitle(), inquiryDto.getQuestion(), member));
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
}
