package com.msdn.security.model;

import lombok.Data;

/**
 * @author hresh
 * @date 2021/9/4 9:32
 * @description
 * 表单输入信息，包括用户和密码
 */
@Data
public class AuthenticationRequest {

    private String username;
    private String password;
}
