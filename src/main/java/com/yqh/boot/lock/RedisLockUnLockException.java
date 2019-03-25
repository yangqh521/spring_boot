package com.yqh.boot.lock;

/**
 * 加锁失败
 * @author Yang.Qinghui
 */
public class RedisLockUnLockException extends RuntimeException{
	
	private String message;

	public RedisLockUnLockException(String message) {
		super(message);
	}

	
	
}
