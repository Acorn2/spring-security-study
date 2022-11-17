package com.msdn.hresh.security.component;

import cn.hutool.json.JSONUtil;
import com.msdn.hresh.common.entity.Result;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/9 10:21 下午
 * @description 自定义返回结果：未登录或登录过期
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Cache-Control", "no-cache");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.getWriter().println(JSONUtil.parse(Result.unauthorized(authException.getMessage())));
    response.getWriter().flush();
  }
}
