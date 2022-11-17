package com.msdn.hresh.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/9/14 9:07 下午
 * @description
 */
@Configuration
public class SpringDocConfig {

  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
        .info(new Info().title("jwt-project-practice API")
            .version("v1.0.0")
            .license(new License().name("Apache 2.0").url("https://www.hreshhao.com")))
        .externalDocs(new ExternalDocumentation()
            .description("SpringSecurity与JWT结合使用")
            .url("https://www.hreshhao.com"));
  }
}
