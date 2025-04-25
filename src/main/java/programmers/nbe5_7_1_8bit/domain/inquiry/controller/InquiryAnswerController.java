package programmers.nbe5_7_1_8bit.domain.inquiry.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryAnswerDto;
import programmers.nbe5_7_1_8bit.domain.inquiry.service.InquiryAnswerService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inq/ans")
public class InquiryAnswerController {

  private final InquiryAnswerService inquiryAnswerService;

  @GetMapping("/{id}")
  String viewInquiryAnswer(@PathVariable("id") Long id, Model model) {
    model.addAttribute("inquiryAnswer", inquiryAnswerService.findById(id));
    return "";
  }

  @PostMapping()
  String saveInquiryAnswer(@ModelAttribute InquiryAnswerDto inquiryAnswerDto) {
    inquiryAnswerService.save(inquiryAnswerDto);
    return "";
  }
}
