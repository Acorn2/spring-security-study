package com.msdn.hresh.security.component;

import com.msdn.hresh.config.IgnoreUrlsConfig;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/10 9:16 上午
 * @description
 */
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

  @Autowired
  private IgnoreUrlsConfig ignoreUrlsConfig;
  @Autowired
  private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

  @Autowired
  public void myAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
    super.setAccessDecisionManager(dynamicAccessDecisionManager);
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
    //OPTIONS请求直接放行
    if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
      fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
      return;
    }
    //白名单请求直接放行
    PathMatcher pathMatcher = new AntPathMatcher();
    for (String path : ignoreUrlsConfig.getUrls()) {
      if (pathMatcher.match(path, request.getRequestURI())) {
        fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        return;
      }
    }
    //此处会调用AccessDecisionManager中的decide方法进行鉴权操作
    InterceptorStatusToken token = super.beforeInvocation(fi);
    try {
      fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
    } finally {
      super.afterInvocation(token, null);
    }
  }

  @Override
  public Class<?> getSecureObjectClass() {
    return FilterInvocation.class;
  }

  @Override
  public SecurityMetadataSource obtainSecurityMetadataSource() {
    return dynamicSecurityMetadataSource;
  }

}
