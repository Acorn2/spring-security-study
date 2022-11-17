package com.msdn.security.model;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hresh
 * @date 2021/5/4 21:13
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

  private Long id;
  private String username;
  private String password;
  private String fullname;
  private String mobile;

  // 用户权限
  private Set<String> authorities;
}
