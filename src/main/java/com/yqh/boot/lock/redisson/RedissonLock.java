package com.yqh.boot.lock.redisson;


import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import com.yqh.boot.lock.RedisLockKeyType;

@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {
	
	// 缓存key值
	RedisLockKeyType value();
	
	// 阻塞时间
	long waitTime() default 30L;
	
	// 自动解锁时间
	long leaseTime() default 5L;
	
	// 时间单位
	TimeUnit timeUnit() default TimeUnit.SECONDS;
 
}
