package com.yqh.boot.lock;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 *	分布式锁 
 * @author Yang.Qinghui
 */
@Component
public class RedisDistributedLock {
	
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    /**
     * 加锁
     * @param resource
     * @return
     */
    public boolean lock(String resource) {
        return this.lock(resource, RedisLockConfig.KEY_LOCK_TIME);
    }
    
    /**
     * 加锁
     * @param key
     * @param expireTime
     * @return
     */
    public boolean lock(String key, long expireTime) {

        long now = System.currentTimeMillis();
        long lockExpireTime = now + expireTime;
        System.out.println("[ RedisDistributedLock ] lockExpireTime:" + lockExpireTime);

        boolean executeResult = redisTemplate.opsForValue().setIfAbsent( key, String.valueOf(lockExpireTime));
        if (executeResult) {
            redisTemplate.expire( key, RedisLockConfig.KEY_EXPRIRE_TIME, TimeUnit.SECONDS);
            System.out.println("[ RedisDistributedLock lock ] success !");
            return true;
        }
        
        Object value = this.getKeyWithRetry(key, RedisLockConfig.KEY_TRY_TIMES);
        if (value != null) {
            long oldExpireTime = Long.parseLong((String)value);
            System.out.println("[ RedisDistributedLock lock ] oldExpireTime:" + oldExpireTime);
            if (oldExpireTime <= now) {
                String value2 = redisTemplate.opsForValue().getAndSet(key, String.valueOf(lockExpireTime));
                long currentExpireTime = Long.parseLong(value2);
                System.out.println("[ RedisDistributedLock lock ] currentExpireTime:" + currentExpireTime);
                if(currentExpireTime == oldExpireTime){
                    redisTemplate.expire(key, RedisLockConfig.KEY_EXPRIRE_TIME, TimeUnit.SECONDS);
                    System.out.println("[ RedisDistributedLock lock ] success !");
                    return true;
                }else{
                	System.out.println("[ RedisDistributedLock lock ] fail !");
                    return false;
                }
            }
        }
    
        System.out.println("[ RedisDistributedLock lock ] fail !");
        return false;
    }

    /**
     * 定次重取
     * @param key
     * @param retryTimes
     * @return
     */
    private Object getKeyWithRetry(String key, int retryTimes) {
        int failTime = 0;
        while (failTime < retryTimes) {
            try {
                return redisTemplate.opsForValue().get(key);
            } catch (Exception exception) {
                failTime ++;
                if (failTime >= retryTimes) {
                    throw exception;
                }
            }
        }
        return null;
    }

    /**
     * 解锁
     * @param key
     * @return
     */
    public boolean unlock(String key) {
    	System.out.println("[ RedisDistributedLock unlock ] success !");
        redisTemplate.delete(key);
        return true;
    }
    
    
}
