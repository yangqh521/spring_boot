package com.yqh.boot.service.impl;

import org.springframework.stereotype.Service;
import com.yqh.boot.lock.RedisLock;
import com.yqh.boot.lock.RedisLockKeyType;
import com.yqh.boot.service.LockService;

@Service
public class LockServieImpl implements LockService{

	@Override
	@RedisLock(value=RedisLockKeyType.TEST_2, expireTime=40 * 6000, waitTime = 10 * 1000)
	public void lockTest2(long time, String logid, Integer seconds)
			throws Exception {
		Thread.sleep(seconds * 1000);
	}

}
