package com.msdn.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/10/18 10:31 下午
 * @description
 */
@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private static ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {
    response.setContentType("application/json;charset=utf-8");
    response.getWriter().write(objectMapper.writeValueAsString("登录失败，请重试！"));
  }
}
