package com.msdn.hresh.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/10/20 9:12 下午
 * @description
 */
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {

  private static ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void onExpiredSessionDetected(SessionInformationExpiredEvent event)
      throws IOException, ServletException {
    String msg = "登录超时或已在另一台机器登录，您被迫下线！";
    HttpServletResponse response = event.getResponse();
    response.setContentType("application/json;charset=utf-8");
    response.getWriter().write(objectMapper.writeValueAsString(msg));
  }
}
