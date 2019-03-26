package com.yqh.boot.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 测试自定义注解使用
 * @author Yang.Qinghui
 */
//@Aspect
@Component
public class TestAdvice {
	
//	@Pointcut("execution(* com.yqh.boot..*(..))")
	@Pointcut("@annotation(com.yqh.boot.lock.RedisLock)")
	public void pointcut(){};
    
    @Before("execution(* com.yqh.boot..*(..))")
    public void doBefore() {
		System.out.println("前置通知。。。。。。");
	}
	
  	@Around(value="@annotation(redisLock)")
	public void doAround(ProceedingJoinPoint proceedingJoinPoint, RedisLock redisLock) throws Throwable{
    	System.out.println("环绕前通知。。。。。。");
    	System.out.println("redisLock value >>> " + redisLock.value().getLockKey());
    	proceedingJoinPoint.proceed();
    	System.out.println("环绕后通知。。。。。。");
    }
    
	@After("@annotation(com.yqh.boot.lock.RedisLock)")
	public void doAfter() {
		System.out.println("后置通知....");
		
	}


}
