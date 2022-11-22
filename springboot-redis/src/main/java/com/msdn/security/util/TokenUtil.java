package com.msdn.security.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/10/14 3:02 下午
 * @description
 */
public class TokenUtil {

  private TokenUtil() {
  }

  public static String getTokenFromAuthorizationHeader(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");
    Pattern authorizationPattern = Pattern
        .compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);//<token>的值就是真实的表达式配置的值

    if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
      return null;
    }
    Matcher matcher = authorizationPattern.matcher(authorization);
    if (!matcher.matches()) {
      return null;
    }
    return matcher.group("token");//从上面的正则表达式中获取token
  }
}
