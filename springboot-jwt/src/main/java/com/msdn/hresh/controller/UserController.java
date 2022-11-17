package com.msdn.hresh.controller;

import com.msdn.hresh.common.entity.Result;
import com.msdn.hresh.dto.request.UserRequest;
import com.msdn.hresh.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class UserController {

  @Autowired
  private UserService userService;
  @Value("${jwt.tokenHeader}")
  private String tokenHeader;
  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @PostMapping("/register")
  public Result register(@RequestBody UserRequest userRequest) {
    userService.register(userRequest);
    return Result.ok();
  }

  @PostMapping("/login")
  public Result<Object> login(@RequestParam("username") String username,
      @RequestParam("password") String password) {
    String token = userService.login(username, password);
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    return Result.ok(tokenMap);
  }

  @PostMapping("/refreshToken")
  public Result<Object> refreshToken(@RequestBody HttpServletRequest request) {
    String token = request.getHeader(tokenHeader);
    String refreshToken = userService.refreshToken(token);
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", refreshToken);
    tokenMap.put("tokenHead", tokenHead);
    return Result.ok(tokenMap);
  }

}
