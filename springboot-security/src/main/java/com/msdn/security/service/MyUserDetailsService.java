package com.msdn.security.service;

import cn.hutool.core.util.StrUtil;
import com.msdn.security.mapper.PermissionMapper;
import com.msdn.security.mapper.UserMapper;
import com.msdn.security.model.Permission;
import com.msdn.security.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author hresh
 * @date 2021/5/4 20:35
 * @description 自定义UserDetailsService
 */
@Component
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

  private final UserMapper userMapper;
  private final PermissionMapper permissionMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //根据账号去数据库查询...
    User user = userMapper.selectByUserName(username);
    if (Objects.isNull(user)) {
      return null;
    }
    List<String> permissions = findPermissionsByUserId(user.getId().toString());
    String[] perArray = new String[permissions.size()];
    permissions.toArray(perArray);
    UserDetails userDetails =
        org.springframework.security.core.userdetails.User.withUsername(username)
            .password(user.getPassword())
            .authorities(perArray).build();
    return userDetails;
  }

  /**
   * 从会话中获取当前登录用户名
   *
   * @return
   */
  public String getUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!authentication.isAuthenticated()) {
      return "";
    }
    Object principal = authentication.getPrincipal();
    String username = "";
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    return username;
  }

  /**
   * 根据用户id查询用户权限
   *
   * @param userId
   * @return
   */
  public List<String> findPermissionsByUserId(String userId) {
    if (StrUtil.isEmpty(userId)) {
      return new ArrayList<>();
    }
    List<Permission> permissionList = permissionMapper.findPermissionsByUserId(userId);
    return permissionList.stream().map(Permission::getName).collect(Collectors.toList());
  }

}
