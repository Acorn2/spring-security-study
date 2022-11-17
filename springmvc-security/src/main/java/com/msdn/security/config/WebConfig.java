package com.msdn.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration//就相当于springmvc.xml文件
@EnableWebMvc
@ComponentScan(basePackages = "com.msdn.security"
    , includeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
public class WebConfig implements WebMvcConfigurer {

  //视图解析器
  @Bean
  public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/view/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }

  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("redirect:/login");
  }

}
