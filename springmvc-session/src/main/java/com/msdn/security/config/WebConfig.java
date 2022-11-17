package com.msdn.security.config;

import com.msdn.security.interceptor.SimpleAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author hresh
 * @date 2021/5/4 11:13
 * @description 相当于springmvc.xml文件
 **/
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.msdn.security"
    , includeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
public class WebConfig implements WebMvcConfigurer {


  @Autowired
  private SimpleAuthenticationInterceptor simpleAuthenticationInterceptor;

  //视图解析器
  @Bean
  public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/view/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }

  /**
   * 视图跳转控制器
   *
   * @param registry
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("login");
  }

  //拦截器配置
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(simpleAuthenticationInterceptor).addPathPatterns("/r/**");
  }
}
