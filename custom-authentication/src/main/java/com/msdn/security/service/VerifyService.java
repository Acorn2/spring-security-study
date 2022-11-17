package com.msdn.security.service;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import java.util.Properties;
import org.springframework.stereotype.Service;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/13 9:25 下午
 * @description
 */
@Service
public class VerifyService {

  public Producer getProducer() {
    Properties properties = new Properties();
    properties.setProperty("kaptcha.image.width", "150");
    properties.setProperty("kaptcha.image.height", "50");
    properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
    properties.setProperty("kaptcha.textproducer.char.length", "4");
    Config config = new Config(properties);
    DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
    defaultKaptcha.setConfig(config);
    return defaultKaptcha;
  }
}
