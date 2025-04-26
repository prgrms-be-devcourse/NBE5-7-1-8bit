package programmers.nbe5_7_1_8bit.domain.inquiry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto;
import programmers.nbe5_7_1_8bit.domain.inquiry.repository.InquiryRepository;
import programmers.nbe5_7_1_8bit.global.config.HibernateFilterManager;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InquiryServiceImpl implements InquiryService {

  private final HibernateFilterManager hibernateFilterManager;

  private final InquiryRepository inquiryRepository;

  @Override
  public void save(InquiryDto inquiryDto) {
    inquiryRepository.save(
        InquiryDto.of(inquiryDto.getTitle(), inquiryDto.getQuestion(), inquiryDto.getName()));
  }

  @Override
  public InquiryDto findById(Long inquiryId) {
    return inquiryRepository.findInquiryDtoById(inquiryId);
  }

  @Override
  public Page<InquiryDto> findPage(int page, int offset) {
    hibernateFilterManager.enableFilter("softDeleteFilter", "isRemoved", false);
    Page<InquiryDto> result = inquiryRepository.findPageForManager(PageRequest.of(page, offset,
        Sort.by(Direction.DESC,"createdAt")));
    hibernateFilterManager.disableFilter("softDeleteFilter");
    return result;
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
