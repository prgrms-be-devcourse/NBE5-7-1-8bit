package programmers.nbe5_7_1_8bit.domain.manager.entity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import programmers.nbe5_7_1_8bit.domain.manager.utils.SessionUtils;

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

    sessionUtils.accessSessionAuth(session);
  }
}
