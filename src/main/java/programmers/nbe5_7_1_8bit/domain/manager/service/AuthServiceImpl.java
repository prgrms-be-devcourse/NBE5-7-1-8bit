package programmers.nbe5_7_1_8bit.domain.manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import programmers.nbe5_7_1_8bit.domain.inquiry.utils.PasswordUtils;
import programmers.nbe5_7_1_8bit.domain.manager.repository.ManagerRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final ManagerRepository managerRepository;

  @Override
  public boolean login(String password) {
    String encryptPassword = managerRepository.findById(0L).map(it -> it.getPassword()).orElseThrow();
    return PasswordUtils.checkPw(password, encryptPassword);
  }

  @Override
  public void changePassword(String password) {
    managerRepository.findById(0L).get().update(password);
  }

  @Override
  public void resetPassword() {
    changePassword("0000");
  }
}
