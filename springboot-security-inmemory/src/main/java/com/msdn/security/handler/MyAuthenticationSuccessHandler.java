package com.msdn.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/10/18 10:29 下午
 * @description
 */
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private static ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    response.setContentType("application/json;charset=utf-8");
    response.getWriter().write(objectMapper.writeValueAsString("登录成功"));
  }
}
