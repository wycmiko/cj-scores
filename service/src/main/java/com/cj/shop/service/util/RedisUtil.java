package com.cj.shop.service.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yuchuanWeng( )
 * @date 2018/5/22
 * @since 1.0
 */
@Component
@Slf4j
public class RedisUtil {
    @Value("${spring.redis.expire-time}")
    public Long expiraTime;
    private static final String prefix = "cj_mall:";
    @Autowired
    private RedisTemplate redisTemplate;

    public RedisUtil() {
    }

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    public void remove(String... keys) {
        String[] var2 = keys;
        int len = keys.length - 1;
        for (int i = 0; i < len; i++) {
            String key =  var2[i];
            this.remove(key);
        }
    }

    public void removePattern(String pattern) {
        pattern = prefix + pattern;
        Set keys = this.redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            this.redisTemplate.delete(keys);
        }

    }

    public void remove(String key) {
        key = prefix + key;
        if (this.exists(key)) {
            this.redisTemplate.delete(key);
        }

    }

    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key).booleanValue();
    }

    public Object get(String key) {
        key = prefix + key;
        Object result = null;
        ValueOperations operations = this.redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }


    public boolean set(String key, Object value) {
        boolean result = false;
        key = prefix + key;
        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            e.set(key, value);
            result = true;
        } catch (Exception var5) {
            log.error("RedisUtil set exception", var5);
        }
        return result;
    }


    public boolean setValueDefaultExpire(String key, Object value) {
        boolean result = false;
        key = prefix + key;
        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            e.set(key, value);
            this.redisTemplate.expire(key, expiraTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception var5) {
            log.error("RedisUtil set exception", var5);
        }
        return result;
    }

    public boolean setByJson(String key, Object value) {
        boolean result = false;
        key = prefix + key;
        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            value = JSONObject.toJSONString(value);
            e.set(key, value);
            result = true;
        } catch (Exception var5) {
            log.error("RedisUtil set exception", var5);
        }
        return result;
    }

    public boolean setByJsonDefaultTime(String key, Object value) {
        boolean result = false;
        key = prefix + key;
        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            value = JSONObject.toJSONString(value);
            e.set(key, value);
            this.redisTemplate.expire(key, expiraTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception var5) {
            log.error("RedisUtil set exception", var5);
        }
        return result;
    }

    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;
        key = prefix + key;
        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            e.set(key, value);
            this.redisTemplate.expire(key, expireTime.longValue(), TimeUnit.SECONDS);
            result = true;
        } catch (Exception var6) {
            log.error("RedisUtil set exception", var6);
        }

        return result;
    }

    public boolean setIfAbsent(String key, Object value) {
        boolean result = false;
        key = prefix + key;
        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            result = e.setIfAbsent(key, value).booleanValue();
        } catch (Exception var5) {
            log.error("RedisUtil setIfAbsent exception", var5);
        }

        return result;
    }

    public boolean setIfAbsent(String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            result = e.setIfAbsent(key, value).booleanValue();
            if (result) {
                this.redisTemplate.expire(key, expireTime.longValue(), TimeUnit.SECONDS);
            }
        } catch (Exception var6) {
            log.error("RedisUtil setIfAbsent exception", var6);
        }

        return result;
    }

    public boolean tryLock(String key, Long cacheSeconds) {
        boolean isLock = false;

        try {
            isLock = this.setIfAbsent(key, "", cacheSeconds);
        } catch (Exception var5) {
            log.error("RedisUtil tryLock exception", var5);
        }

        return isLock;
    }
}
