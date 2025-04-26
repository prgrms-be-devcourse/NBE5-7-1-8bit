package programmers.nbe5_7_1_8bit.domain.inquiry.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PasswordUtilsTest {

  @Test
  void checkPw() {
    String p1 = "1234";
    String p2 = "1234";

    String enc = PasswordUtils.encrpt(p1);
    Assertions.assertThat(PasswordUtils.checkPw(p2, enc)).isTrue();
  }
}
