package com.msdn.hresh.controller;

import com.msdn.hresh.common.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/10 8:46 下午
 * @description
 */
@RestController
public class ResourceController {

  @GetMapping("/home/level1")
  public Result getHomeLevel1() {
    return Result.ok("获取访问Home目录下的Level1的权限");
  }

  @GetMapping("/home/level2")
  public Result getHomeLevel2() {
    return Result.ok("获取访问Home目录下的Level2的权限");
  }

  @GetMapping("/customer/level1")
  public Result getCustomerLevel1() {
    return Result.ok("获取访问Customer目录下的Level1的权限");
  }

  @GetMapping("/customer/level2")
  public Result getCustomerLevel2() {
    return Result.ok("获取访问Customer目录下的Level2的权限");
  }

  @GetMapping("/product/level1")
  public Result getProductLevel1() {
    return Result.ok("获取访问Product目录下的Level3的权限");
  }

  @GetMapping("/product/level2")
  public Result getProductLevel2() {
    return Result.ok("获取访问Product目录下的Level的权限");
  }
}
