package com.msdn.security.service;

import com.msdn.security.dto.MyUserDetails;
import com.msdn.security.mapper.PermissionMapper;
import com.msdn.security.mapper.UserMapper;
import com.msdn.security.model.Permission;
import com.msdn.security.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    List<String> permissions = findPermissionsByUserId(user.getId());

    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(permissions.size());
    permissions.forEach(name -> grantedAuthorities.add(new SimpleGrantedAuthority(name)));

    return MyUserDetails.builder().username(username)
        .password(user.getPassword()).enabled(true).authorities(grantedAuthorities).build();
  }

  /**
   * 根据用户id查询用户权限
   *
   * @param userId
   * @return
   */
  public List<String> findPermissionsByUserId(Long userId) {
    if (Objects.isNull(userId)) {
      return new ArrayList<>();
    }
    List<Permission> permissionList = permissionMapper.findPermissionsByUserId(userId);
    return permissionList.stream().map(Permission::getName).collect(Collectors.toList());
  }

}
