package programmers.nbe5_7_1_8bit.domain.manager.entity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import programmers.nbe5_7_1_8bit.domain.manager.utils.SessionUtils;
import programmers.nbe5_7_1_8bit.global.exception.IllegalManagerAccessException;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminAspect {

  private final SessionUtils sessionUtils;

  @Before("@annotation(programmers.nbe5_7_1_8bit.domain.manager.entity.AdminOnly)")
  public void checkAdminAccess() {
    HttpServletRequest req = ((ServletRequestAttributes)
        RequestContextHolder.currentRequestAttributes()).getRequest();
    HttpSession session = req.getSession(true);

    try {
      sessionUtils.accessSessionAuth(session);
    } catch (IllegalManagerAccessException e) {
      try {
        HttpServletResponse response = ((ServletRequestAttributes)
            RequestContextHolder.currentRequestAttributes()).getResponse();

        if (response != null) {
          response.sendRedirect("/manager/login"); // 원하는 경로로 리다이렉트
        }
      } catch (IOException ioException) {
        log.error("리다이렉트 실패", ioException);
      }

    }
  }
}
