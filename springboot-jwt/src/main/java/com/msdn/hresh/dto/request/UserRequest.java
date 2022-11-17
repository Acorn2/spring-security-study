package com.msdn.hresh.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/10 8:48 下午
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

  private String username;
  private String password;
  private String phone;
}
