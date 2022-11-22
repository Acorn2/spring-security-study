package com.msdn.security.service;


import com.msdn.security.dto.UserRequest;

/**
 * 用户服务.
 *
 * @author hresh
 * @date 2021/9/4 9:33
 */
public interface UserService {

  String login(String username, String password);

  void register(UserRequest userRequest);

  String generateAuthCode(String username);
}
