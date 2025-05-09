package programmers.nbe5_7_1_8bit.domain.manager.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import programmers.nbe5_7_1_8bit.domain.manager.entity.AdminOnly;
import programmers.nbe5_7_1_8bit.domain.manager.service.AuthService;
import programmers.nbe5_7_1_8bit.domain.manager.utils.SessionUtils;
import programmers.nbe5_7_1_8bit.domain.order.dto.request.OrderUpdateRequest;
import programmers.nbe5_7_1_8bit.domain.order.dto.response.OrderDetailResponse;
import programmers.nbe5_7_1_8bit.domain.order.dto.response.OrderListResponse;
import programmers.nbe5_7_1_8bit.domain.order.entity.Status;
import programmers.nbe5_7_1_8bit.domain.order.service.OrderService;

@Controller
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ManagerController {

  private final AuthService authService;
  private final SessionUtils sessionUtils;
  private final OrderService orderService;

  @GetMapping("/login")
  String viewLogin() {
    return "admin/auth/manager-auth";
  }

  @PostMapping("/login")
  String login(@RequestParam String password, HttpServletRequest request,
      HttpServletResponse response, HttpSession session,
      Model model) {
    if (authService.login(password)) {
      sessionUtils.makeSessionAuth(request, response, session);
      return "redirect:/api/products";
    }
    model.addAttribute("errorCode", "잘못된 관리자 비밀번호입니다.");
    return "admin/auth/manager-auth";
  }

  @GetMapping("/logout")
  @AdminOnly
  @ResponseBody
  ResponseEntity<Void> logout(HttpServletResponse response, HttpSession session) {
    sessionUtils.removeSessionAuth(response, session);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/changePassword")
  @AdminOnly
  String viewChangePassword() {
    return "admin/auth/admin-change-password";
  }

  @PostMapping("/changePassword")
  @AdminOnly
  String changePassword(@ModelAttribute("password") String password) {
    authService.changePassword(password);
    return "redirect:/api/admin/login";
  }

  @GetMapping("/orders")
  @AdminOnly
  public String orderList(Model model) {
    List<OrderListResponse> orders = orderService.getAllOrders();
    model.addAttribute("orders", orders);
    return "admin/orders/list";
  }

  @GetMapping("/orders/{orderId}")
  @AdminOnly
  public String orderDetail(@PathVariable Long orderId, Model model) {
    OrderDetailResponse detail = orderService.getOrderDetailById(orderId);
    model.addAttribute("order", detail);
    return "admin/orders/detail";
  }

  @GetMapping("/orders/{orderId}/update")
  @AdminOnly
  public String editOrderForm(@PathVariable Long orderId, Model model) {
    OrderDetailResponse order = orderService.getOrderDetailById(orderId);
    model.addAttribute("order", order);
    model.addAttribute("orderStatuses", Status.values());
    return "admin/orders/form";
  }

  @PostMapping("/orders/{orderId}/update")
  @AdminOnly
  public String updateOrder(@PathVariable Long orderId,
      @ModelAttribute OrderUpdateRequest request,
      RedirectAttributes redirectAttributes) {
    orderService.adminUpdateOrder(orderId, request);
    redirectAttributes.addFlashAttribute("message", "주문이 성공적으로 수정되었습니다.");
    return "redirect:/api/admin/orders";
  }

  @PostMapping("orders/{orderId}/cancel")
  @AdminOnly
  public String cancelProduct(@PathVariable Long orderId, RedirectAttributes redirectAttributes) {
    orderService.adminCancelOrder(orderId);
    redirectAttributes.addFlashAttribute("message", "주문이 성공적으로 삭제되었습니다.");
    return "redirect:/api/admin/orders";
  }

}
