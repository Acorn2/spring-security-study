package com.msdn.security.interceptor;

import cn.hutool.core.util.StrUtil;
import com.msdn.security.model.User;
import com.msdn.security.service.RedisService;
import com.msdn.security.util.TokenUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author hresh
 * @date 2021/9/4 21:09
 * @description
 */
@Component
public class SimpleAuthenticationInterceptor implements HandlerInterceptor {

  @Autowired
  private RedisService redisService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String token = TokenUtil.getTokenFromAuthorizationHeader(request);
    if (StrUtil.isBlank(token)) {
      return false;
    }
    User user = (User) redisService.get(token);
    if (Objects.isNull(user)) {
      return false;
    }
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
