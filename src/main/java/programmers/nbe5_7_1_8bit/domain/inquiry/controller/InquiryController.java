package programmers.nbe5_7_1_8bit.domain.inquiry.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import programmers.nbe5_7_1_8bit.domain.inquiry.entity.InquiryDto;
import programmers.nbe5_7_1_8bit.domain.inquiry.service.InquiryService;

@Controller
@RequestMapping("/inq")
@RequiredArgsConstructor
public class InquiryController {

  private final InquiryService inquiryService;

  @GetMapping("/page")
  String viewInquiryTable(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int offset,
      Model model) {
    model.addAttribute("page", page);
    model.addAttribute("offset", offset);

    model.addAttribute("inquires", inquiryService.findPage(page, offset));
    return "inquiry-list-form";
  }

  @GetMapping("/new")
  String viewInputInquiry(Model model) {
    model.addAttribute("inquiry", new InquiryDto());
    return "inquiry-input-form";
  }

  @PostMapping("/new")
  String saveInquiry(@RequestParam InquiryDto inquiryDto) {
    inquiryService.save(inquiryDto);
    return "redirect:/page";
  }

  @GetMapping("/{id}")
  String findInquiry(@PathVariable("id") Long id, Model model) {
    model.addAttribute("inquiry", inquiryService.findById(id));
    return "";
  }

  @PutMapping()
  String updateInquiry(@RequestParam InquiryDto inquiryDto) {
    inquiryService.update(inquiryDto);
    return "";
  }

  @DeleteMapping()
  String softDeleteInquiry(@RequestParam Long inquiryId) {
    inquiryService.softDelete(inquiryId);
    return "";
  }
}
