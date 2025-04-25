package programmers.nbe5_7_1_8bit.global.config;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HibernateFilterManager {

  private final EntityManager entityManager;

  public void enableFilter(String filterName, String paramName, Object paramValue) {
    Session session = entityManager.unwrap(Session.class);
    Filter filter = session.enableFilter(filterName);
    filter.setParameter(paramName, paramValue);
  }

  public void disableFilter(String filterName) {
    Session session = entityManager.unwrap(Session.class);
    session.disableFilter(filterName);
  }
}
