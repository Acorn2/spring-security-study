package com.msdn.security.service;

import com.msdn.security.model.UserRequest;
import com.msdn.security.model.User;

/**
 * @author hresh
 * @date 2021/9/4 9:33
 * @description 认证服务，将前台传递来的用户名和密码与数据库里做比较
 */
public interface AuthenticationService {

  /**
   * 用户认证
   *
   * @param userRequest
   * @return
   */
  User authentication(UserRequest userRequest);
}
