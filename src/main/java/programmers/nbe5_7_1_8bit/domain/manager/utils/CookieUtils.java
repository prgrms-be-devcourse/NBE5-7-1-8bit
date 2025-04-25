package programmers.nbe5_7_1_8bit.domain.manager.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

  @Value("${cookie.session-cookie-key}")
  private String sessionCookieKey;
  @Value("${cookie.max-age}")
  private int maxAge;
  @Value("${cookie.http-only}")
  private boolean httpOnly;
  @Value("${cookie.secure}")
  private boolean secure;

  public Cookie makeSessionIdCookie(HttpSession session) {
    Cookie cookie = new Cookie(sessionCookieKey, session.getId());
    cookie.setHttpOnly(httpOnly);
    cookie.setSecure(secure);
    cookie.setPath("/");
    return cookie;
  }

  public void removeSessionCookie(HttpServletResponse response) {
    Cookie cookie = new Cookie(sessionCookieKey, "");
    cookie.setMaxAge(0);
    cookie.setHttpOnly(httpOnly);
    cookie.setSecure(secure);
    cookie.setPath("/");
    response.addCookie(cookie);
  }
}
