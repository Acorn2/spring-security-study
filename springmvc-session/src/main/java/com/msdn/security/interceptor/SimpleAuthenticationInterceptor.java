package com.msdn.security.interceptor;

import com.msdn.security.model.User;
import com.msdn.security.util.CookieUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author hresh
 * @date 2021/9/4 21:09
 * @description
 */
@Component
public class SimpleAuthenticationInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String userSessionId = CookieUtil.getUserCookie(request);
    Object attribute = request.getSession().getAttribute(userSessionId);
    if (Objects.isNull(attribute)) {
      writeContent(response, "请先登录");
    }
    User user = (User) attribute;
    String requestURI = request.getRequestURI();
    if (user.getAuthorities().contains("p1") && requestURI.contains("r1")) {
      return true;
    }
    if (user.getAuthorities().contains("p2") && requestURI.contains("r2")) {
      return true;
    }
    if (requestURI.contains("resource")) {
      return true;
    }

    writeContent(response, "权限不足，无法访问");
    return false;
  }

  private void writeContent(HttpServletResponse response, String msg) throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter writer = response.getWriter();
    writer.print(msg);
    writer.close();
    response.resetBuffer();
  }
}
