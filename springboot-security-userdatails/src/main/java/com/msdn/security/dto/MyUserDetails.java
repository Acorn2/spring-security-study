package com.msdn.security.dto;

import java.util.Collection;
import lombok.Builder;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
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

  private String username;
  private String password;
  private boolean enabled;
  private Collection<? extends GrantedAuthority> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
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
    return enabled;
  }
}
