package programmers.nbe5_7_1_8bit.domain.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping
  String index() {
    return "redirect:/api/products";
  }

}
