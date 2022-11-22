package com.msdn.security.config;

import com.msdn.security.filter.AuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author hresh
 * @date 2021/5/4 20:28
 * @description
 */
@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationTokenFilter authenticationTokenFilter() {
    return new AuthenticationTokenFilter();
  }

  //安全拦截机制（最重要）
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()   //屏蔽CSRF控制，即spring security不再限制CSRF
        .authorizeRequests()
        .antMatchers("/register").permitAll()
        .antMatchers("/verify-code").permitAll()
        .antMatchers("/login").permitAll()
        .antMatchers("/home/level1").hasAuthority("home")
        .antMatchers("/customer/level1").hasAuthority("customer")
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(authenticationTokenFilter(),
            UsernamePasswordAuthenticationFilter.class);// 自定义认证过滤器
    return http.build();
  }
}
