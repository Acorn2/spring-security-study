package com.msdn.security.controller;

import cn.hutool.core.util.StrUtil;
import com.msdn.security.model.User;
import com.msdn.security.model.UserRequest;
import com.msdn.security.service.AuthenticationService;
import com.msdn.security.service.RedisService;
import com.msdn.security.util.TokenUtil;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class LoginController {

  @Autowired
  private AuthenticationService authenticationService;
  @Autowired
  private RedisService redisService;

  @PostMapping(value = "/login")
  public String login(UserRequest request) {
    try {
      return authenticationService.authentication(request);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 测试资源1
   *
   * @return
   */
  @GetMapping(value = "/r/r1")
  public String r1(HttpServletRequest request) {
    String token = TokenUtil.getTokenFromAuthorizationHeader(request);
    if (StrUtil.isBlank(token)) {
      return null;
    }
    User user = (User) redisService.get(token);
    String fullName = Objects.nonNull(user) ? user.getFullname() : "匿名";
    return fullName + " 访问资源1";
  }

  /**
   * 测试资源2
   *
   * @return
   */
  @RequestMapping(value = "/r/r2")
  public String r2(HttpServletRequest request) {
    String token = TokenUtil.getTokenFromAuthorizationHeader(request);
    if (StrUtil.isBlank(token)) {
      return null;
    }
    User user = (User) redisService.get(token);
    String fullName = Objects.nonNull(user) ? user.getFullname() : "匿名";

    return fullName + " 访问资源2";
  }

}
