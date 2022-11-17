package com.msdn.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author hresh
 * @date 2021/5/4 11:13
 * @description
 */
@Configuration
@ComponentScan(basePackages = "com.msdn.security"
        , excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value =
        Controller.class)})
public class ApplicationConfig {
    //在此配置除了Controller的其它bean，比如：数据库链接池、事务管理器、业务bean等。
}
