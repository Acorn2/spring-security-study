package com.msdn.security.controller;

import com.msdn.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class LoginController {

  @Autowired
  private MyUserDetailsService userDetailsService;

  @RequestMapping(value = "/login-success")
  public String loginSuccess() {
    return userDetailsService.getUserName() + " 登录成功";
  }

  /**
   * 测试资源1
   *
   * @return
   */
  @GetMapping(value = "/r/r1")
  @PreAuthorize("hasAuthority('p1')") //拥有p1权限才可以访问
  public String r1() {
    return userDetailsService.getUserName() + " 访问资源1";
  }

  /**
   * 测试资源2
   *
   * @return
   */
  @GetMapping(value = "/r/r2")
  @PreAuthorize("hasAuthority('p2')") //拥有p2权限才可以访问
  public String r2() {
    return userDetailsService.getUserName() + " 访问资源2";
  }

  @GetMapping(value = "/session/invalid")
  public String sessionInvalid() {
    return "session已失效，请重新认证";
  }
}
