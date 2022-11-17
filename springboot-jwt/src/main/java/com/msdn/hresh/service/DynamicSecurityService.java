package com.msdn.hresh.service;

import com.msdn.hresh.mapper.PermissionMapper;
import com.msdn.hresh.model.Permission;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/16 10:11 上午
 * @description 读取权限表，封装到MAP中
 */
@Service
public class DynamicSecurityService {

  @Autowired
  private PermissionMapper permissionMapper;

  // 加载资源ANT通配符和资源对应MAP
  public Map<String, ConfigAttribute> loadDataSource() {
    Map<String, ConfigAttribute> urlAndResourceNameMap = new ConcurrentHashMap<>();
    List<Permission> permissions = permissionMapper.findAll();
    permissions.forEach(permission -> urlAndResourceNameMap
        .put(permission.getUrl(), new SecurityConfig(permission.getName())));
    return urlAndResourceNameMap;
  }
}
