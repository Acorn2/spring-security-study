package com.msdn.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hresh
 * @date 2021/5/4 21:13
 * @description
 */
@TableName("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  private Long id;
  private String username;
  private String password;
  private String phone;
}
