package com.msdn.hresh.dto;

import com.msdn.hresh.model.Permission;
import com.msdn.hresh.model.User;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/10/20 4:48 下午
 * @description
 */
@Setter
@Builder
public class MyUserDetails implements UserDetails {

  private User user;
  private List<Permission> permissionList;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return permissionList.stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getName())).collect(
            Collectors.toList());
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return !Objects.isNull(user);
  }
}
