package com.yqh.boot.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *	分布式锁注解
 * @author Yang.Qinghui
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisLock {

	RedisLockKeyType value();
	
	boolean isBlock() default true;
	
	int expireTime() default RedisLockConfig.KEY_LOCK_TIME;
	
	int waitTime() default RedisLockConfig.BLOCK_WAITE_TIME;
	
}
