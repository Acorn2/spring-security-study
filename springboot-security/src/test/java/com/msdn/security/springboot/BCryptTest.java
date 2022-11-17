package com.msdn.security.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author hresh
 * @date 2021/5/4 20:40
 * @description
 */
public class BCryptTest {

  @Test
  public void getBCryptCode() {
    String hashpw = BCrypt.hashpw("123", BCrypt.gensalt());
    System.out.println(hashpw);
    boolean checkpw = BCrypt.checkpw("123", hashpw);
    System.out.println(checkpw);
    String hashpw2 = BCrypt.hashpw("456", BCrypt.gensalt());
    System.out.println(hashpw2);
  }
}
