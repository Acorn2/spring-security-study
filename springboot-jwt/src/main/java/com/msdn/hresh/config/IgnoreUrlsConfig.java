package com.msdn.hresh.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/9 9:28 下午
 * @description
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

  private List<String> urls = new ArrayList<>();

}
