package com.msdn.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class LoginController {

  @GetMapping(value = "logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "退出成功";
  }

  @RequestMapping(value = "/login-success")
  public String loginSuccess() {
    return " 登录成功";
  }

  /**
   * 测试资源1
   *
   * @return
   */
  @GetMapping(value = "/r/r1")
  public String r1() {
    return " 访问资源1";
  }

  /**
   * 测试资源2
   *
   * @return
   */
  @GetMapping(value = "/r/r2")
  public String r2() {
    return " 访问资源2";
  }

  @GetMapping("/hello")
  public String hello() {
    return "hello,hresh";
  }
}
