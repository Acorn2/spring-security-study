package com.msdn.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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

  //安全拦截机制（最重要）
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()   //屏蔽CSRF控制，即spring security不再限制CSRF
        .authorizeRequests()
        .antMatchers("/login.html").permitAll()
        .antMatchers("/r/r1").hasAuthority("p1")
        .antMatchers("/r/r2").hasAuthority("p2")
        .antMatchers("/r/**").authenticated()//所有/r/**的请求必须认证通过
        .anyRequest().authenticated()
        .and()
        .formLogin()//允许表单登录
        .loginPage("/login.html")
        .loginProcessingUrl("/doLogin")
//        .successHandler((req, resp, authentication) -> {
//          Object principal = authentication.getPrincipal();
//          resp.setContentType("application/json;charset=utf-8");
//          PrintWriter out = resp.getWriter();
//          out.write(new ObjectMapper().writeValueAsString(principal));
//          out.flush();
//          out.close();
//        })
        .successForwardUrl("/login-success")//自定义登录成功的页面地址
        .and()
        .sessionManagement()
//                .invalidSessionUrl("/session/invalid")
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login-view?logout")
    ;
    return http.build();
  }
}
