package com.msdn.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/10/17 4:35 下午
 * @description
 */
@RestController
@Slf4j
public class HelloController {

  @GetMapping("/hello")
  public String hello(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.setAttribute("name", "hresh");
    log.info(" session id is " + session.getId());
    return "hello,hresh";
  }

  @GetMapping("/hresh")
  public String sayHello(HttpServletRequest request) {
    HttpSession session = request.getSession();
    String name = (String) session.getAttribute("name");
    log.info("input code is " + name + " session id is " + session.getId());
    return "hello,world";
  }

  @GetMapping(value = "/r/r1")
  @PreAuthorize("hasAuthority('p1')")
  public String r1() {
    return " 访问资源1";
  }

  @GetMapping(value = "/r/r2")
  @PreAuthorize("hasAuthority('p2')")
  public String r2() {
    return " 访问资源2";
  }
}
