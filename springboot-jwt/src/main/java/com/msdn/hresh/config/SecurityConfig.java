package com.msdn.hresh.config;

import com.msdn.hresh.security.component.DynamicAccessDecisionManager;
import com.msdn.hresh.security.component.DynamicSecurityFilter;
import com.msdn.hresh.security.component.DynamicSecurityMetadataSource;
import com.msdn.hresh.security.component.JwtAuthenticationTokenFilter;
import com.msdn.hresh.security.component.MyAccessDeniedHandler;
import com.msdn.hresh.security.component.MyAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/10 10:34 上午
 * @description
 */
@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public IgnoreUrlsConfig ignoreUrlsConfig() {
    return new IgnoreUrlsConfig();
  }

  @Bean
  public MyAccessDeniedHandler myAccessDeniedHandler() {
    return new MyAccessDeniedHandler();
  }

  @Bean
  public MyAuthenticationEntryPoint myAuthenticationEntryPoint() {
    return new MyAuthenticationEntryPoint();
  }

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    return new JwtAuthenticationTokenFilter();
  }

  @ConditionalOnBean(name = "dynamicSecurityService")
  @Bean
  public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
    return new DynamicAccessDecisionManager();
  }

  @ConditionalOnBean(name = "dynamicSecurityService")
  @Bean
  public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
    return new DynamicSecurityMetadataSource();
  }

  @ConditionalOnBean(name = "dynamicSecurityService")
  @Bean
  public DynamicSecurityFilter dynamicSecurityFilter() {
    return new DynamicSecurityFilter();
  }

  //跨域
  @Autowired
  private CorsFilter corsFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
        .authorizeRequests();
    //不需要保护的资源路径允许访问
    for (String url : ignoreUrlsConfig().getUrls()) {
      registry.antMatchers(url).permitAll();
    }
    //允许跨域请求的OPTIONS请求
    registry.antMatchers(HttpMethod.OPTIONS)
        .permitAll();

    registry.and()
        .csrf().disable()   //屏蔽CSRF控制，即spring security不再限制CSRF
        .authorizeRequests()
        .anyRequest().authenticated();

    registry.and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(corsFilter, CsrfFilter.class)//跨域配置
        .exceptionHandling()  //异常处理，下面是自定义的两个异常
        .accessDeniedHandler(myAccessDeniedHandler())//授权异常捕获
        .authenticationEntryPoint(myAuthenticationEntryPoint())//认证异常捕获
        .and()
        .addFilterBefore(jwtAuthenticationTokenFilter(),
            UsernamePasswordAuthenticationFilter.class);// 自定义认证过滤器

    //添加动态权限校验过滤器
    registry.and().addFilterBefore(dynamicSecurityFilter(), FilterSecurityInterceptor.class);
    return http.build();
  }
}
