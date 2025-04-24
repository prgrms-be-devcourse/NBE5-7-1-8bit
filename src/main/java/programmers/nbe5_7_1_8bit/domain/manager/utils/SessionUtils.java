package programmers.nbe5_7_1_8bit.domain.manager.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import programmers.nbe5_7_1_8bit.global.exception.IllegalManagerAccessException;

@Component
@RequiredArgsConstructor
public class SessionUtils {

  @Value("${session.authenticate-key}")
  private String authenticateKey;
  @Value("${session.max-inactive-interval}")
  private int maxInactiveInterval;
  private final CookieUtils cookieUtils;

  public void makeSessionAuth(HttpServletRequest request, HttpServletResponse response,
      HttpSession session) {
    session.invalidate(); // 세션 고정 공격 방지
    session = request.getSession(true);

    session.setAttribute(authenticateKey, true);
    session.setMaxInactiveInterval(maxInactiveInterval);

    response.addCookie(cookieUtils.makeSessionIdCookie(session));
  }

  public void accessSessionAuth(HttpSession session) {
    Boolean isAuth = getSessionAttribute(session);
    if (isAuth == null || !isAuth) {
      throw new IllegalManagerAccessException();
    }
  }

  public void removeSessionAuth(HttpServletResponse response, HttpSession session) {
    session.invalidate();

    cookieUtils.removeSessionCookie(response);
  }

  public Boolean getSessionAttribute(HttpSession session) {
    return (Boolean) session.getAttribute(authenticateKey);
  }

}
