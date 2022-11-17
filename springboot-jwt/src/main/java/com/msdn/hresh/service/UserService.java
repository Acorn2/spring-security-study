package com.msdn.hresh.service;

import com.msdn.hresh.common.exception.BusinessException;
import com.msdn.hresh.dto.request.UserRequest;
import com.msdn.hresh.mapper.UserMapper;
import com.msdn.hresh.model.User;
import com.msdn.hresh.security.util.JwtTokenUtil;
import com.msdn.hresh.struct.UserStruct;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/10/20 4:53 下午
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final MyUserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserStruct userStruct;
  private final UserMapper userMapper;

  public String login(String username, String password) {
    String token = null;
    try {

      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        BusinessException.fail("密码不正确");
      }
      if (!userDetails.isEnabled()) {
        BusinessException.fail("帐号已被禁用");
      }
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateToken(userDetails);
    } catch (AuthenticationException e) {
      log.error("登录异常，detail" + e.getMessage());
    }
    return token;
  }

  public void register(UserRequest userRequest) {
    User user = userMapper.selectByUserName(userRequest.getUsername());
    if (Objects.nonNull(user)) {
      BusinessException.fail("用户名已存在！");
    }
    String encodePassword = passwordEncoder.encode(userRequest.getPassword());
    User obj = userStruct.toUser(userRequest);
    obj.setPassword(encodePassword);
    userMapper.insert(obj);
  }

  public String refreshToken(String oldToken) {
    return jwtTokenUtil.refreshHeadToken(oldToken);
  }
}
