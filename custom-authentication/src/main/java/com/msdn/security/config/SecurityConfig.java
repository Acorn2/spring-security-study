package com.msdn.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msdn.security.common.entity.Result;
import java.io.PrintWriter;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/13 8:47 下午
 * @description
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    ProviderManager manager = new ProviderManager(Arrays.asList(myAuthenticationProvider()));
    return manager;
  }

  @Bean
  @Override
  protected UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("hresh").password("123").roles("admin").build());
    return manager;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/verify-code").permitAll()
        .antMatchers("/code").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .successHandler((req, resp, auth) -> {
          resp.setContentType("application/json;charset=utf-8");
          PrintWriter out = resp.getWriter();
          out.write(new ObjectMapper().writeValueAsString(Result.ok(auth.getPrincipal())));
          out.flush();
          out.close();
        })
        .failureHandler((req, resp, e) -> {
          resp.setContentType("application/json;charset=utf-8");
          PrintWriter out = resp.getWriter();
          out.write(new ObjectMapper().writeValueAsString(Result.failed(e.getMessage())));
          out.flush();
          out.close();
        })
        .permitAll();
  }

  @Bean
  MyAuthenticationProvider myAuthenticationProvider() {
    MyAuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider();
    myAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    myAuthenticationProvider.setUserDetailsService(userDetailsService());
    return myAuthenticationProvider;
  }

}
