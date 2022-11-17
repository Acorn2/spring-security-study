package com.msdn.security.service.impl;

import com.msdn.security.model.UserRequest;
import com.msdn.security.model.User;
import com.msdn.security.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author hresh
 * @date 2021/9/4 9:35
 * @description
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private static Map<String, User> userMap = new HashMap<>();

  static {
    Set<String> authoritie1 = new HashSet<>();
    authoritie1.add("p1");
    Set<String> authoritie2 = new HashSet<>();
    authoritie2.add("p2");
    userMap.put("zhangsan", new User(1010L, "zhangsan", "123", "张三", "133443", authoritie1));
    userMap.put("lisi", new User(1011L, "lisi", "456", "李四", "144553", authoritie2));
  }

  @Override
  public User authentication(UserRequest userRequest) {
    User user = getUserByName(userRequest.getUsername());
    if (Objects.isNull(user)) {
      throw new RuntimeException("查询不到该用户");
    }

    if (!Objects.equals(user.getPassword(), userRequest.getPassword())) {
      throw new RuntimeException("账号或密码错误");
    }

    return user;
  }

  /**
   * 模仿从表中根据用户名查询用户信息
   *
   * @param username
   * @return
   */
  public User getUserByName(String username) {
    return userMap.get(username);
  }
}
