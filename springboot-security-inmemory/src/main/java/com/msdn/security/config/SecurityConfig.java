package com.msdn.security.config;

import com.msdn.security.handler.MyAuthenticationFailureHandler;
import com.msdn.security.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/10/17 4:33 下午
 * @description
 */
@Configuration
public class SecurityConfig {

  @Autowired
  private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
  @Autowired
  private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

  @Bean
  public UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("zhangsan").password("123").authorities("p1").build());
    manager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
    return manager;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().antMatchers("/hello");
  }

  /**
   * loginProcessingUrl、usernameParameter、passwordParameter需要和 login.html 中的配置一致
   *
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.authorizeRequests()
//        .antMatchers("/r/r1").hasAuthority("p1")
//        .antMatchers("/r/r2").hasAuthority("p2")
//        .anyRequest().authenticated()
//        .and()
//        .formLogin()
//        .successHandler(myAuthenticationSuccessHandler)
//        .permitAll()
//        .and()
//        .csrf().disable();
    http.authorizeRequests()  //表示开启权限配置
        .antMatchers("/login.html").permitAll()
        .antMatchers("/doLogin").permitAll()
        .antMatchers("/r/r1").hasAuthority("p1")
        .antMatchers("/r/r2").hasAuthority("p2")
        .anyRequest().authenticated() //表示所有的请求都要经过认证之后才能访问
        .and()  // 链式编程写法
        .formLogin()  //开启表单登录配置
        .loginPage("/login.html") // 配置登录页面地址
        .loginProcessingUrl("/doLogin") //配置登录接口地址
//        .defaultSuccessUrl()  //登录成功后的跳转页面
//        .successForwardUrl()
//        .failureUrl() //登录失败后的跳转页面
//        .usernameParameter("username")  //登录用户名的参数名称
//        .passwordParameter("password")  // 登录密码的参数名称
        .successHandler(
            myAuthenticationSuccessHandler) //前后端分离的情况，并不想通过defaultSuccessUrl进行页面跳转，只需要返回一个json数据来告知前端
//        .failureHandler(myAuthenticationFailureHandler) // 同理，替代failureUrl
        .permitAll()
//        .and()
//        .logout()
//        .logoutSuccessHandler((req, resp, auth) -> {
//          resp.setContentType("application/ison;charset=utf-8");
//          Map<String, Object> result = new HashMap<>();
//          result.put("status", 200);
//          result.put("msg", "注销成功");
//          ObjectMapper om = new ObjectMapper();
//          String s = om.writeValueAsString(result);
//          resp.getWriter().write(s);
//
//        })
        .and()
        .csrf().disable();// 禁用CSRF防御功能，测试可以先关闭
    return http.build();
  }

}
