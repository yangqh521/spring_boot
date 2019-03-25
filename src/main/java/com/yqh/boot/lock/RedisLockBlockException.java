package com.yqh.boot.lock;


/**
 * 阻塞超时异常
 * @author Yang.Qinghui
 */
public class RedisLockBlockException extends RuntimeException{
	
	private String message;

	public RedisLockBlockException(String message) {
		super(message);
	}
	
	

}
