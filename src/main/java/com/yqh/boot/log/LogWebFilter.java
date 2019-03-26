package com.yqh.boot.log;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;

import com.alibaba.fastjson.JSONObject;
import com.yqh.boot.log.aop.LogWebContext;

@Order(1)
@WebFilter(filterName = "logWebFilter", urlPatterns = "/*")
public class LogWebFilter implements Filter {

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
		
    }
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		
		 HttpServletRequest request = (HttpServletRequest) servletRequest;
		Map<String, String[]> parameterMap = request.getParameterMap();
		String requestURI = request.getRequestURI();
		
		long startTime = System.currentTimeMillis();
		
		LogWebContext logWebContext = LogWebContext.getInstance();
		logWebContext.putLog(LogKeyType.URI.getLogKey(), requestURI);
		logWebContext.putLog(LogKeyType.PAMRAMS.getLogKey(), JSONObject.toJSONString(parameterMap));
		
		System.out.println("[ " + requestURI +" ] params:" + JSONObject.toJSONString(parameterMap));
		
		// 向下执行
		filterChain.doFilter(servletRequest, servletResponse);
		
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String costTime = (System.currentTimeMillis() - startTime) + "ms";
		
		logWebContext.putLog(LogKeyType.COST_TIME.getLogKey(), costTime);
		
		logWebContext.printLog();
		logWebContext.clear();
		System.out.println("[ " + requestURI +" ] cost:" + costTime);
	}
	
	@Override
    public void destroy() {
		
    }

}
