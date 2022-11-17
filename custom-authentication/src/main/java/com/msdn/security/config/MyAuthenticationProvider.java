package com.msdn.security.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/13 8:46 下午
 * @description
 */
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    // 验证码比对
    HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes()).getRequest();
    String code = req.getParameter("code");
    HttpSession session = req.getSession(false);
    String verify_code = (String) session.getAttribute("verify_code");
    if (code == null || verify_code == null || !code.equals(verify_code)) {
      throw new AuthenticationServiceException("验证码错误");
    }
    // 密码比对
    super.additionalAuthenticationChecks(userDetails, authentication);
  }
}
