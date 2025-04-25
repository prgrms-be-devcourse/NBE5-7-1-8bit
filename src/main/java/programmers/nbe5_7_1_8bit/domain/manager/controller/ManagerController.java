package programmers.nbe5_7_1_8bit.domain.manager.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import programmers.nbe5_7_1_8bit.domain.manager.entity.AdminOnly;
import programmers.nbe5_7_1_8bit.domain.manager.service.AuthService;
import programmers.nbe5_7_1_8bit.domain.manager.utils.SessionUtils;

@Controller("/manager")
@RequiredArgsConstructor
public class ManagerController {

  private final AuthService authService;
  private final SessionUtils sessionUtils;

  @GetMapping("/login")
  String viewLogin() {
    return "";
  }

  @PostMapping("/login")
  String login(@RequestParam String password, HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      Model model) {
    if (authService.login(password)) {
      sessionUtils.makeSessionAuth(request, response, session);
      return "redirect:/dashboard";
    }
    model.addAttribute("errorCode", "invalid login");
    return "";
  }

  @GetMapping("/logout")
  @AdminOnly
  @ResponseBody
  ResponseEntity logout(HttpServletResponse response, HttpSession session) {
    sessionUtils.removeSessionAuth(response, session);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/dashboard")
  @AdminOnly
  String dashboard() {
    return "dashboard";
  }
}
