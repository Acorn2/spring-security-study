package com.msdn.security.struct;

import com.msdn.security.dto.UserRequest;
import com.msdn.security.model.User;
import org.mapstruct.Mapper;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/10 8:50 下午
 * @description
 */
@Mapper(componentModel = "spring")
public interface UserStruct {

  User toUser(UserRequest userRequest);
}
