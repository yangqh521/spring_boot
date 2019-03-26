package com.yqh.boot.log.aop;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yqh.boot.log.LogKeyType;

/**
 * 测试日志前后打印
 * @author Yang.Qinghui
 */
//@Aspect
@Component
public class LogWebAspect {
	
	@Pointcut("execution(* com.yqh.boot.controller..*(..))")
	public void pointcut(){};
	
	@Before("pointcut()")
	public void  doBefore(){
		RequestContext requestContext = RequestContext.getInstance();
		HttpServletRequest request = requestContext.getRequest();
		Map<String, String[]> parameterMap = request.getParameterMap();
		String requestURI = request.getRequestURI();
		
		LogWebContext logWebContext = LogWebContext.getInstance();
		logWebContext.putLog(LogKeyType.START_TIME.getLogKey(), new Date().getTime() + "");
		logWebContext.putLog(LogKeyType.URI.getLogKey(), requestURI);
		logWebContext.putLog(LogKeyType.PAMRAMS.getLogKey(), JSONObject.toJSONString(parameterMap));
		
		System.out.println("[ " + requestURI +" ] params:" + JSONObject.toJSONString(parameterMap));
	}
	
	@After("pointcut()")
	public void doAfter(){
		LogWebContext logWebContext = LogWebContext.getInstance();
		Long startTime = Long.valueOf(logWebContext.getLog(LogKeyType.START_TIME.getLogKey()));
		String requestURI = logWebContext.getLog(LogKeyType.URI.getLogKey());
		String costTime = (System.currentTimeMillis() - startTime) + "ms";
		logWebContext.putLog(LogKeyType.COST_TIME.getLogKey(), costTime);
		
		logWebContext.printLog();
		
		logWebContext.clear();
		System.out.println("[ " + requestURI +" ] cost:" + costTime);
	}

}
