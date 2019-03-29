package com.yqh.boot.controller;

import javax.persistence.Basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yqh.boot.common.AppResponse;
import com.yqh.boot.lock.RedisDistributedLock;
import com.yqh.boot.lock.RedisLock;
import com.yqh.boot.lock.RedisLockKeyType;
import com.yqh.boot.service.LockService;


@RestController
@RequestMapping("/lock")
public class LockAction {
	
	@Autowired
	private LockService lockService;
	
	@Autowired
	RedisDistributedLock redisDistributedLock;
	
	@RequestMapping("/test1")
	public AppResponse lockTest1(String logid, Integer seconds){
		AppResponse resp = new AppResponse();
		long time = System.currentTimeMillis();
		System.out.println("[ " + time +" lockTest1 " + logid + " ] start ~~~");
		if(redisDistributedLock.lock("redis_lock_test", 60 * 1000)){
			System.out.println("[ " + time +" lockTest1 " + logid + " ] lock success ~~~");
			try {
				System.out.println("[ " + time +" lockTest1 " + logid + " ] sleep ing ...");
				Thread.sleep(seconds * 1000);
				System.out.println("[ " + time +" lockTest1 " + logid + " ] sleep over ...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("[ " + time +" lockTest1 " + logid + " ] unlock ---");
			redisDistributedLock.unlock("redis_lock_test");
		}else{
			System.out.println("[ " + time +" lockTest1 " + logid + " ] lock fail !!!");
			resp.setData(1);
		}
		System.out.println("[ " + time +" lockTest1 " + logid + " ] end ~~~");
		return resp;
	}
	
	
	@RequestMapping("/test2")
	public AppResponse lockTest2(String logid, Integer seconds){
		AppResponse resp = new AppResponse();
		long time = System.currentTimeMillis();
		System.out.println("[ " + time +" lockTest2 " + logid + " ] start ~~~");
		try {
			System.out.println("[ " + time +" lockTest2 " + logid + " ] sleep ing ...");
			String result = lockService.lockTest3(time, logid, seconds);
			System.out.println("[ " + time +" lockTest2 " + logid + " ] result: " + result);
			System.out.println("[ " + time +" lockTest2 " + logid + " ] sleep over ...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[ " + time +" lockTest2 " + logid + " ] end ~~~");
		return resp;
	}
	

}
