package com.zr.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by 廖师兄
 * 2017-08-07 23:55
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value) {
        /**
         * 1. 通过setIfAbsent()方法尝试加锁，如果当前锁不存在，返回加锁成功。
         * 2. 如果锁已经存在则获取锁的过期时间，和当前时间比较，如果锁已经过期，
         *      则获取上一个锁的时间，如果上一个锁时间和当前时间相同，返回加锁成功。
         */
        if(redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }

        /**
         *   解决死锁的问题
         */
        //currentValue=A   这两个线程的value都是B  其中一个线程拿到锁
        //获取锁的超时时间
        String currentValue = redisTemplate.opsForValue().get(key);

        //如果锁过期
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {

            //获取上一个锁的时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);

            //如下判断来确保，所超时时，当有A,B两个线程刚进来，只有一个获得锁
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {

                return true;
            }
        }

        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e) {
            log.error("【redis分布式锁】解锁异常, {}", e);
        }


    }


    public static void main(String[] args) {


    }


}
