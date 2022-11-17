package com.msdn.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author hresh
 * @date 2021/5/4 21:13
 * @description
 */
@Data
@AllArgsConstructor
public class UserDTO {

    public static final String SESSION_USER_KEY = "_user";

    private Long id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;

    // 用户权限
    private Set<String> authorities;
}
