package com.msdn.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/10/20 4:53 下午
 * @description
 */
@Service
public class UserService {

  /**
   * 从会话中获取当前登录用户名
   *
   * @return
   */
  public String getUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!authentication.isAuthenticated()) {
      return "";
    }
    Object principal = authentication.getPrincipal();
    String username = "";
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    return username;
  }
}
