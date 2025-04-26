package programmers.nbe5_7_1_8bit.domain.inquiry.repository;

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
      "select new programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto(i.id,i.title,i.question,i.name,i.inquiryAnswer.answer,i.createdAt,i.updatedAt) from Inquiry i left join i.inquiryAnswer ia where i.id = :id")
  InquiryDto findInquiryDtoById(@Param("id") Long id);

  @Query(
      "select new programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto(i.id,i.title,i.name,i.createdAt,i.updatedAt) "
          + "from Inquiry i")
  Page<InquiryDto> findPageForManager(Pageable pageable);

  @Query("select new programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryAnswerDto(i.inquiryAnswer.id,i.inquiryAnswer.answer) from Inquiry i where i.id = :id")
  InquiryAnswerDto findInquiryAnswerDtoById(@Param("id") Long id);
}
