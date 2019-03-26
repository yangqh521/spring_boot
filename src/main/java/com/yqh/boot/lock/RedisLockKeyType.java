package com.yqh.boot.lock;


/**
 * 锁主键
 * @author Yang.Qinghui
 */

public enum RedisLockKeyType {

	TEST("test"),
	TEST_1("123"),
	TEST_2("abc"),
	REDISSON("redisson"),
	CASH_SEND("cash_send");
	
	private String lockKey;

	RedisLockKeyType(String lockKey) {
		this.lockKey = lockKey;
	}

	public String getLockKey() {
		return lockKey;
	}

	public void setLockKey(String lockKey) {
		this.lockKey = lockKey;
	}
	
	
	
}
