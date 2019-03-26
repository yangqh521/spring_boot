package com.yqh.boot.lock.redisson;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yqh.boot.lock.RedisLockUnLockException;

@Aspect
@Component
public class RedissonLockAdvice {
	
	@Autowired
	RedissonClient redissonClient;
	
	@Around("@annotation(redissonLock)")
	public Object processAround(ProceedingJoinPoint proceedingJoinPoint, RedissonLock redissonLock) throws Throwable {
		
		RLock fairLock = redissonClient.getFairLock("redisson_lock_test");
		System.out.println("[RedissonLockAdvice processAround] getFairLock ~~~");
		try {
			
			long startTime=System.currentTimeMillis();	
			
			// 尝试加锁，最多等待waitTime毫秒，上锁以后leaseTime毫秒自动解锁
			boolean result = fairLock.tryLock(redissonLock.waitTime(), redissonLock.leaseTime(), redissonLock.timeUnit());
			
			long costTime = System.currentTimeMillis() - startTime;
			
			if(result){
				System.out.println("[RedissonLockAdvice processAround] tryLock SUCCESS ! costTime:" + costTime + "ms");
				return proceedingJoinPoint.proceed();
			}else{
				System.out.println("[RedissonLockAdvice processAround] tryLock FAIL ! costTime:" + costTime + "ms");
				throw new RedisLockUnLockException("tryLock FAIL !");
			}
		}finally{
			System.out.println("[RedissonLockAdvice processAround] unlock SUCCESS !");
			if(fairLock != null && fairLock.isHeldByCurrentThread()){
				fairLock.unlock();
			}
		}
	}

	
	
}
