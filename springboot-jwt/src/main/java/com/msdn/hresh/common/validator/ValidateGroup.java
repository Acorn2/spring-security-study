package com.msdn.hresh.common.validator;

import javax.validation.groups.Default;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/2 9:41 下午
 * @description @validated分组校验，区分新增和编辑以及其它情况下的参数校验
 */
public interface ValidateGroup {

  /**
   * 新增
   */
  interface Add extends Default {

  }

  /**
   * 删除
   */
  interface Delete {

  }

  /**
   * 编辑
   */
  interface Edit extends Default {

  }
}
