package programmers.nbe5_7_1_8bit.global.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import programmers.nbe5_7_1_8bit.domain.manager.entity.Manager;
import programmers.nbe5_7_1_8bit.domain.manager.repository.ManagerRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer {

  private final ManagerRepository managerRepository;

  @PostConstruct
  @Transactional
  public void init() {
    if (managerRepository.count() == 0) {
      managerRepository.save(new Manager());
    }
  }
}
