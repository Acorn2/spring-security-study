package com.msdn.security.service.impl;

import static cn.hutool.core.text.CharSequenceUtil.isBlank;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.msdn.security.common.exception.BusinessException;
import com.msdn.security.dto.UserRequest;
import com.msdn.security.mapper.UserMapper;
import com.msdn.security.model.User;
import com.msdn.security.service.MyUserDetailsService;
import com.msdn.security.service.RedisService;
import com.msdn.security.service.UserService;
import com.msdn.security.struct.UserStruct;
import java.util.Objects;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author hresh
 * @date 2021/9/4 9:35
 * @description
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final RedisService redisService;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final UserStruct userStruct;
  private final MyUserDetailsService userDetailsService;

  @Value("${redis.key.prefix.authCode}")
  private String REDIS_KEY_PREFIX_AUTH_CODE;
  @Value("${redis.key.expire.time}")
  private Long AUTH_CODE_EXPIRE_SECONDS;


  @Override
  public String login(String username, String password) {
    String token = IdUtil.simpleUUID();
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
      redisService.set(token, username, 60 * 60L);
    } catch (AuthenticationException e) {
      log.error("登录异常，detail" + e.getMessage());
    }

    return token;
  }


  @Override
  public void register(UserRequest userRequest) {
    if (Objects.isNull(userRequest) || isBlank(userRequest.getUsername()) ||
        isBlank(userRequest.getPassword())) {
      BusinessException.fail("账号或密码为空！");
    }
    boolean flag = verifyAuthCode(userRequest.getUsername(), userRequest.getAuthCode());
    if (flag) {
      User user = userMapper.selectByUserName(userRequest.getUsername());
      if (Objects.nonNull(user)) {
        BusinessException.fail("用户名已存在！");
      }
      String encodePassword = passwordEncoder.encode(userRequest.getPassword());
      User obj = userStruct.toUser(userRequest);
      obj.setPassword(encodePassword);
      userMapper.insert(obj);
    }
  }

  public String generateAuthCode(String username) {
    StringBuilder sb = new StringBuilder();
    Random random = RandomUtil.getRandom();
    for (int i = 0; i < 6; i++) {
      sb.append(random.nextInt(10));
    }
    String code = sb.toString();
    redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + username, code, AUTH_CODE_EXPIRE_SECONDS);
    return code;
  }

  private boolean verifyAuthCode(String username, String authCode) {
    if (!StringUtils.hasLength(authCode)) {
      BusinessException.fail("请输入验证码！");
    }
    String realAuthCode = (String) redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + username);
    return authCode.equals(realAuthCode);
  }

}
