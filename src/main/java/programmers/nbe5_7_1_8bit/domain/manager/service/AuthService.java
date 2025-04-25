package programmers.nbe5_7_1_8bit.domain.manager.service;

public interface AuthService {

  boolean login(String password);

  void changePassword(String password);

  void resetPassword();
}
