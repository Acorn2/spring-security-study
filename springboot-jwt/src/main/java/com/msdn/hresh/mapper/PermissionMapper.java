package com.msdn.hresh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msdn.hresh.model.Permission;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hresh
 * @date 2021/5/5 10:45
 * @description
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findPermissionsByUserId(Long userId);

    List<Permission> findAll();
}
