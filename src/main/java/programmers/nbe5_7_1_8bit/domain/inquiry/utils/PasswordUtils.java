package programmers.nbe5_7_1_8bit.domain.inquiry.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PasswordUtils {

  public static String encrpt(String plainPw) {
    return BCrypt.withDefaults().hashToString(12, plainPw.toCharArray());
  }

  public static boolean checkPw(String plainPw, String encryptPw) {
    return BCrypt.verifyer().verify(plainPw.toCharArray(), encryptPw).verified;
  }
}
