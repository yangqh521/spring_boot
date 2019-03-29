package com.yqh.boot.service.impl;

import java.text.SimpleDateFormat;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqh.boot.lock.RedisLock;
import com.yqh.boot.lock.RedisLockKeyType;
import com.yqh.boot.lock.redisson.RedissonLock;
import com.yqh.boot.service.LockService;

@Service
public class LockServieImpl implements LockService{

	@Autowired
	RedissonClient redissonClient;
	
	@Override
	@RedisLock(value=RedisLockKeyType.TEST_2, expireTime=40 * 1000, waitTime =10 * 1000)
	public void lockTest2(long time, String logid, Integer seconds)
			throws Exception {
		Thread.sleep(seconds * 1000);
	}
	
	@Override
	@RedissonLock(value=RedisLockKeyType.REDISSON, waitTime=5*1000, leaseTime=15*1000)
	public String lockTest3(long time, String logid, Integer seconds)
			throws Exception {
		System.out.println("--->>> service lockTest3 begin <<<---");
		Thread.sleep(seconds * 1000);
		RList<Object> list = redissonClient.getList("url_order");
		String string = logid + "" + time;
		list.add(string);
		System.out.println("NOTICE: insert redisson data >>> " +  string);
		Thread.sleep(1000);
		System.out.println("--->>> service lockTest3 end <<<---");
		
		return string;
	}

}
