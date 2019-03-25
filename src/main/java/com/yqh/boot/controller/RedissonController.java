package com.yqh.boot.controller;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yqh.boot.common.AppResponse;

@RestController
@RequestMapping("/redisson")
public class RedissonController {
	
	private static final String LOCK_FLAG = "redissonlock_";
	
	@Autowired
	RedissonClient redissonClient;
	
	@RequestMapping("/test1")
	public AppResponse test1() throws Exception{
		AppResponse resp = new AppResponse();
		System.out.println("[ RedissonController lock ] redissonClient:" + redissonClient);
		Map<String,String> map = redissonClient.getMap("testMap");
		String timeSteap = new Date().getTime() + "";
		System.out.println("timeSteap >>> " + timeSteap);
		map.put(timeSteap, timeSteap);
		resp.setData(timeSteap);
		return resp;
	}
	
	@RequestMapping("/test2")
	public AppResponse test2(){
		AppResponse resp = new AppResponse();
		
		RLock fairLock = redissonClient.getFairLock("");
		try {
			// 最常见的使用方法  
			fairLock.lock();
			// 支持过期解锁功能, 10秒钟以后自动解锁,无需调用unlock方法手动解锁
			fairLock.lock(10, TimeUnit.SECONDS);
			// 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
			boolean res = fairLock.tryLock(100, 10, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			fairLock.unlock();
		}
		
		return resp;
	}

	/**
     * 根据name对进行上锁操作，redissonLock 阻塞事的，采用的机制发布/订阅
     * @param key
     */
    public void lock(String key){
        String lockKey = LOCK_FLAG + key;
        RLock lock = redissonClient.getLock(lockKey);
        //lock提供带timeout参数，timeout结束强制解锁，防止死锁 ：1分钟
        lock.lock(1, TimeUnit.MINUTES);
    }

    /**
     * 根据name对进行解锁操作
     * @param key
     */
    public void unlock(String key){
        String lockKey = LOCK_FLAG + key;
        RLock lock = redissonClient.getLock(lockKey);
        //如果锁被当前线程持有，则释放
        if(lock.isHeldByCurrentThread()){
            lock.unlock();
        }
    }

	
	
}
