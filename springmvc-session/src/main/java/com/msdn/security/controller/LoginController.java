package com.msdn.security.controller;

import static cn.hutool.core.text.CharSequenceUtil.isBlank;

import cn.hutool.core.util.RandomUtil;
import com.msdn.security.model.User;
import com.msdn.security.model.UserRequest;
import com.msdn.security.service.AuthenticationService;
import com.msdn.security.util.CookieUtil;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hresh
 * @date 2021/5/4 11:13
 **/
@Controller
public class LoginController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping(value = "/login")
  public String login(UserRequest request, HttpSession session, Model model,
      HttpServletResponse response) {
    if (Objects.isNull(request) || isBlank(request.getUsername()) ||
        isBlank(request.getPassword())) {
      model.addAttribute("msg", "账号或密码为空");
      return "login";
    }
    try {
      User user = authenticationService.authentication(request);
      String userSessionId = RandomUtil.getRandom().nextInt(10000) + "_user";
      session.setAttribute(userSessionId, user);
      Cookie cookie = CookieUtil.addUserCookie(userSessionId);
      response.addCookie(cookie);

      return "redirect:hello";
    } catch (Exception e) {
      model.addAttribute("msg", e.getMessage());
    }
    return "login";
  }

  @RequestMapping("/hello")
  public String toHello() {
    return "hello";
  }

  @RequestMapping(value = "logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "login";
  }

  /**
   * 测试资源1
   *
   * @return
   */
  @RequestMapping(value = "/r/r1")
  public String r1(HttpServletRequest request, Model model) {
    String userSessionId = CookieUtil.getUserCookie(request);
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute(userSessionId);
    String fullName = Objects.nonNull(user) ? user.getFullname() : "匿名";
    model.addAttribute("text", fullName + " 访问资源1");

    return "resource";
  }

  /**
   * 测试资源2
   *
   * @return
   */
  @RequestMapping(value = "/r/r2")
  public String r2(HttpServletRequest request, Model model) {
    String userSessionId = CookieUtil.getUserCookie(request);
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute(userSessionId);
    String fullName = Objects.nonNull(user) ? user.getFullname() : "匿名";
    model.addAttribute("text", fullName + " 访问资源2");

    return "resource";
  }

}
