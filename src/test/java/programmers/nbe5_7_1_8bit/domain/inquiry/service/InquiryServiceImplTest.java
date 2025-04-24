package programmers.nbe5_7_1_8bit.domain.inquiry.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.Inquiry;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto;
import programmers.nbe5_7_1_8bit.domain.inquiry.repository.InquiryRepository;
import programmers.nbe5_7_1_8bit.domain.member.entity.Member;
import programmers.nbe5_7_1_8bit.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class InquiryServiceImplTest {
  @Mock private InquiryRepository inquiryRepository;
  @Mock private MemberRepository memberRepository;
  @InjectMocks private InquiryServiceImpl inquiryService;

  @Test
  void should_save_when_notExistMember() {
    // given
    InquiryDto inquiryDto = new InquiryDto("t", "q");
    Member member = new Member("e");

    // when
    when(memberRepository.findByEmail(any())).thenReturn(Optional.empty());
    when(memberRepository.save(any())).thenReturn(member);
    inquiryService.save(inquiryDto);

    // then
    verify(memberRepository).save(any(Member.class));
    verify(inquiryRepository).save(any(Inquiry.class));
  }

  @Test
  void should_save_when_ExistMember() {
    // given
    InquiryDto inquiryDto = new InquiryDto("t", "q");
    Member member = new Member("e");

    // when
    when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
    inquiryService.save(inquiryDto);

    // then
    verify(memberRepository,never()).save(any(Member.class));
    verify(inquiryRepository).save(any(Inquiry.class));

  }
}
