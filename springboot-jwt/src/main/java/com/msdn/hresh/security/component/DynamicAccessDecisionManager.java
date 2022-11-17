package com.msdn.hresh.security.component;

import cn.hutool.core.collection.CollUtil;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/10 9:12 上午
 * @description 自定义AccessDecisionManager，通过比对url拦截权限，当然还可以加上httpMethod
 */
public class DynamicAccessDecisionManager implements AccessDecisionManager {

  @Override
  public void decide(Authentication authentication, Object object,
      Collection<ConfigAttribute> configAttributes)
      throws AccessDeniedException, InsufficientAuthenticationException {
    // 当接口未被配置资源时直接放行
    if (CollUtil.isEmpty(configAttributes)) {
      return;
    }
    Iterator<ConfigAttribute> iterator = configAttributes.iterator();
    while (iterator.hasNext()) {
      ConfigAttribute configAttribute = iterator.next();
      //将访问所需资源或用户拥有资源进行比对
      String needAuthority = configAttribute.getAttribute();
      for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
        if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
          return;
        }
      }
    }
    throw new AccessDeniedException("抱歉，您没有访问权限");
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}
