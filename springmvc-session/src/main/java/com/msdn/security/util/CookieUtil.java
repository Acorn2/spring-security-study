package com.msdn.security.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/10/13 2:50 下午
 * @description
 */
public class CookieUtil {


  public static Cookie addUserCookie(String cookieValue) {
    return addCookie("user_session_id", cookieValue);
  }

  public static Cookie addCookie(String cookieName, String cookieValue) {
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setMaxAge(3600);
    cookie.setPath("/");
    return cookie;
  }

  public static String getUserCookie(HttpServletRequest request) {
    return getCookie(request, "user_session_id");
  }

  public static String getCookie(HttpServletRequest request, String cookieName) {
    Cookie[] cookies = request.getCookies();
    String cookieValue = "";
    for (Cookie cookie : cookies) {
      if (cookieName.equals(cookie.getName())) {
        cookieValue = cookie.getValue();
      }
    }
    return cookieValue;
  }

}
