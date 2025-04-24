package programmers.nbe5_7_1_8bit.domain.inquiry.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.Inquiry;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryAnswerDto;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

  @Query(
      "select new programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto(i.title,i.question,i.inquiryAnswer.answer,i.createdAt,i.updatedAt) from Inquiry i where i.id = :id")
  InquiryDto findInquiryDtoById(@Param("id") Long id);

  @Query(
      "select new programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto(i.id,i.title,i.createdAt,i.updatedAt) "
          + "from Inquiry i order by i.createdAt desc")
  Page<InquiryDto> findPageForManager(Pageable pageable);

  @Query("select new programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryAnswerDto(i.inquiryAnswer.id,i.inquiryAnswer.answer) from Inquiry i where i.id = :id")
  InquiryAnswerDto findInquiryAnswerDtoById(@Param("id") Long id);

  List<Inquiry> findAllByMember_Id(Long id);
}
