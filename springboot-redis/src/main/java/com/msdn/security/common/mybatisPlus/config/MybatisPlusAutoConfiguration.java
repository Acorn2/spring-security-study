package com.msdn.security.common.mybatisPlus.config;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import java.time.LocalDateTime;
import java.util.Date;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2021/4/18 17:38
 * @description Mybatis plus 配置类
 */
@ConditionalOnBean({com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration.class})
public class MybatisPlusAutoConfiguration {

  @Bean
  public MetaObjectHandler metaObjectHandler() {
    return new FillFieldConfiguration();
  }

  public static class FillFieldConfiguration implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
      LocalDateTime now = DateUtil.toLocalDateTime(new Date());
      metaObject.setValue("createdDate", now);
      metaObject.setValue("lastModifiedDate", now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
      metaObject.setValue("lastModifiedDate", LocalDateTime.now());
    }
  }

  /**
   * 分页插件
   *
   * @return
   */
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
    PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(
        DbType.MYSQL);
    // 设置最大单页限制数量，默认 500 条，-1 不受限制
    paginationInnerInterceptor.setMaxLimit(500L);
    mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);

    return mybatisPlusInterceptor;
  }
}
