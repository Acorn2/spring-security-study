package com.msdn.hresh.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/2 10:15 下午
 * @description
 */
public class EnumValidatorClass implements ConstraintValidator<EnumValidator, Integer> {

  private String[] values;

  @Override
  public void initialize(EnumValidator enumValidator) {
    this.values = enumValidator.value();
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
    boolean isValid = false;
    if (value == null) {
      //当状态为空时使用默认值
      return true;
    }
    for (int i = 0; i < values.length; i++) {
      if (values[i].equals(String.valueOf(value))) {
        isValid = true;
        break;
      }
    }
    return isValid;
  }
}