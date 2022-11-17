package com.msdn.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/10/19 8:46 下午
 * @description
 */
@TableName("role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

  private Long id;

  private String name;

  private String desc;
}
