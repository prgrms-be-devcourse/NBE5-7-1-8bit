package programmers.nbe5_7_1_8bit.domain.inquiry.utils;

import lombok.experimental.UtilityClass;
import org.mindrot.jbcrypt.BCrypt;

@UtilityClass
public class PasswordUtils {

  public static String encrpt(String plainPw) {
    return BCrypt.hashpw(plainPw, BCrypt.gensalt());
  }

  public static boolean checkPw(String plainPw, String encryptPw) {
    return BCrypt.checkpw(plainPw, encryptPw);
  }
}
