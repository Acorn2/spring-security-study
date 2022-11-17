package com.msdn.hresh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msdn.hresh.model.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author hresh
 * @date 2021/5/4 21:15
 * @description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

  User selectByUserName(String username);
}
