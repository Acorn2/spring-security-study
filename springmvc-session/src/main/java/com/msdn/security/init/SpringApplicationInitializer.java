package com.msdn.security.init;

import com.msdn.security.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author hresh
 * @date 2021/5/4 11:13
 * @description 在init包下定义Spring容器初始化类SpringApplicationInitializer，此类实现WebApplicationInitializer接口，
 * Spring容器启动时加载WebApplicationInitializer接口的所有实现类。 SpringApplicationInitializer相当于web.xml，使用了servlet3.0开发则不需要再定义web.xml
 **/
public class SpringApplicationInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {

  //spring容器，相当于加载 applicationContext.xml
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return null;
  }

  //servletContext，相当于加载springmvc.xml
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[]{WebConfig.class};
  }

  //url-mapping
  @Override
  protected String[] getServletMappings() {
    return new String[]{"/"};
  }
}
