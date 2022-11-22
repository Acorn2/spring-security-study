package com.msdn.security.controller;

import com.msdn.security.common.entity.Result;
import com.msdn.security.dto.UserRequest;
import com.msdn.security.service.UserService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/21 5:42 下午
 */
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/verify-code")
  public Result<Object> getVerifyCodePng(@RequestParam String username) {
    String authCode = userService.generateAuthCode(username);
    return Result.ok(authCode);
  }

  @PostMapping("/register")
  public Result<Object> register(@RequestBody UserRequest request) {
    userService.register(request);
    return Result.ok();
  }

  @PostMapping(value = "/login")
  public Result<Object> login(@RequestParam("username") String username,
      @RequestParam("password") String password) {
    String token = userService.login(username, password);
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", "Bearer ");
    return Result.ok(tokenMap);
  }
}
