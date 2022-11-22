package com.msdn.security.service.impl;

import com.msdn.security.service.RedisService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * redis操作实现类.
 *
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/21 5:08 下午
 */
@Service
public class RedisServiceImpl implements RedisService {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Override
  public void set(String key, Object value, long time) {
    redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
  }

  @Override
  public void set(String key, Object value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public Boolean del(String key) {
    return redisTemplate.delete(key);
  }

  @Override
  public Long del(List<String> keys) {
    return redisTemplate.delete(keys);
  }

  @Override
  public Boolean expire(String key, long time) {
    return redisTemplate.expire(key, time, TimeUnit.SECONDS);
  }

  @Override
  public Long getExpire(String key) {
    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
  }

  @Override
  public Boolean hasKey(String key) {
    return redisTemplate.hasKey(key);
  }

  @Override
  public Long incr(String key, long delta) {
    return redisTemplate.opsForValue().increment(key, delta);
  }

  @Override
  public Long decr(String key, long delta) {
    return redisTemplate.opsForValue().increment(key, -delta);
  }

  @Override
  public Object hashGet(String key, String hashKey) {
    return redisTemplate.opsForHash().get(key, hashKey);
  }

  @Override
  public Boolean hashSet(String key, String hashKey, Object value, long time) {
    redisTemplate.opsForHash().put(key, hashKey, value);
    return expire(key, time);
  }

  @Override
  public void hashSet(String key, String hashKey, Object value) {
    redisTemplate.opsForHash().put(key, hashKey, value);
  }

  @Override
  public Map<Object, Object> hashGetAll(String key) {
    return redisTemplate.opsForHash().entries(key);
  }

  @Override
  public Boolean hashSetAll(String key, Map<String, Object> map, long time) {
    redisTemplate.opsForHash().putAll(key, map);
    return expire(key, time);
  }

  @Override
  public void hashSetAll(String key, Map<String, ?> map) {
    redisTemplate.opsForHash().putAll(key, map);
  }

  @Override
  public void hashDel(String key, Object... hashKey) {
    redisTemplate.opsForHash().delete(key, hashKey);
  }

  @Override
  public Boolean hashHasKey(String key, String hashKey) {
    return redisTemplate.opsForHash().hasKey(key, hashKey);
  }

  @Override
  public Long hashIncr(String key, String hashKey, Long delta) {
    return redisTemplate.opsForHash().increment(key, hashKey, delta);
  }

  @Override
  public Long hashDecr(String key, String hashKey, Long delta) {
    return redisTemplate.opsForHash().increment(key, hashKey, -delta);
  }

  @Override
  public Set<Object> seMembers(String key) {
    return redisTemplate.opsForSet().members(key);
  }

  @Override
  public Long seAdd(String key, Object... values) {
    return redisTemplate.opsForSet().add(key, values);
  }

  @Override
  public Long seAdd(String key, long time, Object... values) {
    Long count = redisTemplate.opsForSet().add(key, values);
    expire(key, time);
    return count;
  }

  @Override
  public Boolean seIsMember(String key, Object value) {
    return redisTemplate.opsForSet().isMember(key, value);
  }

  @Override
  public Long seSize(String key) {
    return redisTemplate.opsForSet().size(key);
  }

  @Override
  public Long seRemove(String key, Object... values) {
    return redisTemplate.opsForSet().remove(key, values);
  }

  @Override
  public List<Object> listRange(String key, long start, long end) {
    return redisTemplate.opsForList().range(key, start, end);
  }

  @Override
  public Long listSize(String key) {
    return redisTemplate.opsForList().size(key);
  }

  @Override
  public Object listIndex(String key, long index) {
    return redisTemplate.opsForList().index(key, index);
  }

  @Override
  public Long listPush(String key, Object value) {
    return redisTemplate.opsForList().rightPush(key, value);
  }

  @Override
  public Long listPush(String key, Object value, long time) {
    Long index = redisTemplate.opsForList().rightPush(key, value);
    expire(key, time);
    return index;
  }

  @Override
  public Long listPushAll(String key, Object... values) {
    return redisTemplate.opsForList().rightPushAll(key, values);
  }

  @Override
  public Long listPushAll(String key, Long time, Object... values) {
    Long count = redisTemplate.opsForList().rightPushAll(key, values);
    expire(key, time);
    return count;
  }

  @Override
  public Long listRemove(String key, long count, Object value) {
    return redisTemplate.opsForList().remove(key, count, value);
  }
}
