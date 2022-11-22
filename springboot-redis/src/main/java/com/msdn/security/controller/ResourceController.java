package com.msdn.security.controller;

import static cn.hutool.core.text.CharSequenceUtil.isBlank;

import com.msdn.security.common.entity.Result;
import com.msdn.security.service.RedisService;
import com.msdn.security.util.TokenUtil;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hresh
 **/
@RestController
@RequiredArgsConstructor
public class ResourceController {

  private final RedisService redisService;

  @GetMapping(value = "/home/level1")
  @PreAuthorize("hasAuthority('home')")
  public Result<Object> getHomeLevel1(HttpServletRequest request) {
    String token = TokenUtil.getTokenFromAuthorizationHeader(request);
    if (isBlank(token)) {
      return null;
    }
    String username = (String) redisService.get(token);
    return Result.ok(username + " 成功访问Home目录下的Level1页面");
  }

  @GetMapping(value = "/customer/level1")
  @PreAuthorize("hasAuthority('customer')")
  public Result<Object> getCustomerLevel1(HttpServletRequest request) {
    String token = TokenUtil.getTokenFromAuthorizationHeader(request);
    if (isBlank(token)) {
      return null;
    }
    String username = (String) redisService.get(token);
    return Result.ok(username + " 成功访问Customer目录下的Level1页面");
  }
}
