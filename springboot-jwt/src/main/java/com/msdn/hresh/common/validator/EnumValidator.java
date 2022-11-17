package com.msdn.hresh.common.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/2 10:14 下午
 * @description
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = EnumValidatorClass.class)
public @interface EnumValidator {

  String[] value() default {};

  boolean required() default true;

  // 校验枚举值不存在时的报错信息
  String message() default "enum is not found";

  //将validator进行分类，不同的类group中会执行不同的validator操作
  Class<?>[] groups() default {};

  //主要是针对bean，很少使用
  Class<? extends Payload>[] payload() default {};
}
