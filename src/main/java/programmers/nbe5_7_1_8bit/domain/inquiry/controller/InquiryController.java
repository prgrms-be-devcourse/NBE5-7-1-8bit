package programmers.nbe5_7_1_8bit.domain.inquiry.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryAnswerDto;
import programmers.nbe5_7_1_8bit.domain.inquiry.service.InquiryAnswerService;
import programmers.nbe5_7_1_8bit.domain.inquiry.service.InquiryService;
import programmers.nbe5_7_1_8bit.domain.manager.entity.AdminOnly;

@Controller
@RequestMapping("/api/inq")
@RequiredArgsConstructor
public class InquiryController {

  private final InquiryService inquiryService;
  private final InquiryAnswerService inquiryAnswerService;

  @GetMapping("/page")
  @AdminOnly
  String viewInquiryTable(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int offset,
      Model model) {
    model.addAttribute("page", page);
    model.addAttribute("offset", offset);

    model.addAttribute("inquires", inquiryService.findPage(page, offset));
    return "/admin/inquiry/inquiry-list-form";
  }

  @GetMapping
  @AdminOnly
  String findInquiry(@RequestParam("inquiryId") Long inquiryId, Model model) {
    model.addAttribute("inquiry", inquiryService.findById(inquiryId));
    return "/admin/inquiry/inquiry-detail-form";
  }

  @DeleteMapping
  @AdminOnly
  @ResponseBody
  ResponseEntity softDeleteInquiry(@RequestParam Long inquiryId) {
    inquiryService.softDelete(inquiryId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/ans")
  @AdminOnly
  String saveInquiryAnswer(@ModelAttribute InquiryAnswerDto inquiryAnswerDto) {
    inquiryAnswerService.save(inquiryAnswerDto);
    return "redirect:/api/inq?inquiryId=" + inquiryAnswerDto.getInquiryId();
  }
}
