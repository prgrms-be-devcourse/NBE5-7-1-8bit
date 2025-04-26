package programmers.nbe5_7_1_8bit.domain.inquiry.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto;
import programmers.nbe5_7_1_8bit.domain.inquiry.service.InquiryService;

@RestController
@RequestMapping("/inq")
@RequiredArgsConstructor
public class InquiryUserController {

  private final InquiryService inquiryService;

  @PostMapping
  ResponseEntity<Void> saveInquiry(@RequestBody InquiryDto inquiryDto) {
    inquiryService.save(inquiryDto);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/page")
  ResponseEntity<Page<InquiryDto>> viewInquiryTable(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int offset) {
    return ResponseEntity.ok(inquiryService.findPage(page, offset));
  }

  @GetMapping
  ResponseEntity<InquiryDto> findInquiry(@RequestParam("inquiryId") Long inquiryId) {
    return ResponseEntity.ok(inquiryService.findById(inquiryId));
  }
}
