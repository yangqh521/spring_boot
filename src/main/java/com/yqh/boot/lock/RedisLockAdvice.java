package com.yqh.boot.lock;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Aspect
@Component
public class RedisLockAdvice {
	
	@Autowired
	private RedisDistributedLock redisDistributedLock;
	
	
    @Around("@annotation(com.yqh.boot.lock.RedisLock)")
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		String methodName = proceedingJoinPoint.getSignature().getName();
        Class<?> targetClazz = proceedingJoinPoint.getTarget().getClass();
        Class<?>[] parameterTypes = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterTypes();
        Method method = targetClazz.getMethod(methodName, parameterTypes);
        RedisLock redisLockAnnoation = method.getDeclaredAnnotation(RedisLock.class);
        
        String key = RedisLockConfig.KEY_PREFIX + redisLockAnnoation.value().getLockKey();
        System.out.println("[ RedisLockAdvice ] methodName:" + methodName);
        System.out.println("[ RedisLockAdvice redisLockAnnoation key ]:" + key);
        System.out.println("[ RedisLockAdvice redisLockAnnoation isBlock ]:" + redisLockAnnoation.isBlock());
        System.out.println("[ RedisLockAdvice redisLockAnnoation waitTime ]:" + redisLockAnnoation.waitTime());
        System.out.println("[ RedisLockAdvice redisLockAnnoation expireTime ]:" + redisLockAnnoation.expireTime());
        
        if(redisLockAnnoation.isBlock()){
        	try {
				long watTime = System.currentTimeMillis() + redisLockAnnoation.waitTime();
				while(!redisDistributedLock.lock(key, redisLockAnnoation.expireTime())){
					if(watTime < System.currentTimeMillis()){	// 阻塞超时
						System.out.println("[ RedisLockAdvice ] throw RedisLockBlockException !");
						throw new RedisLockBlockException("[ " + methodName + " lock ] block !");
					}
					Thread.sleep(100);	// 100ms重试一次
				}
				System.out.println("[ RedisLockAdvice isBlock ] lock success !");
				return proceedingJoinPoint.proceed();
			} catch (Exception exception) {
				System.out.println("[ RedisLockAdvice isBlock ] lock fail !");
				throw exception;
			} finally {
				System.out.println("[ RedisLockAdvice isBlock ] lock unlock !");
				redisDistributedLock.unlock(key);
			}
        }else{
        	try {
				if(!redisDistributedLock.lock(key, redisLockAnnoation.expireTime())){
					System.out.println("[ RedisLockAdvice notBlock ] throw RedisLockUnLockException !");
					throw new RedisLockUnLockException("[ " + methodName + " lock ] fail !");
				}
				System.out.println("[ RedisLockAdvice notBlock] lock success !");
				return proceedingJoinPoint.proceed();
			} catch (Exception exception) {
				System.out.println("[ RedisLockAdvice notBlock ] lock fail !");
				throw exception;
			} finally {
				System.out.println("[ RedisLockAdvice notBlock ] lock unlock !");
				redisDistributedLock.unlock(key);
			}
        }
        
	}
	
	
	
}
