package com.yqh.boot.log.filter;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.yqh.boot.log.LogKeyType;

@Order(1)
@Component
@WebFilter(filterName = "logWebFilter", urlPatterns = "/*")
public class LogWebFilter implements Filter {

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("------>>> LogWebFilter init ~~~");
    }
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		
		System.out.println("------>>> LogWebFilter begin ~~~");
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		Map<String, String[]> parameterMap = request.getParameterMap();
		String requestURI = request.getRequestURI();
		
		long startTime = System.currentTimeMillis();
		
		LogWebContext logWebContext = LogWebContext.getInstance();
		logWebContext.getMap().put(LogKeyType.URI.getLogKey(), requestURI);
		logWebContext.getMap().put(LogKeyType.PAMRAMS.getLogKey(), JSONObject.toJSONString(parameterMap));
		
		System.out.println("[ LogWebFilter " + requestURI +" ] params:" + JSONObject.toJSONString(parameterMap));
		
		// 向下执行
		filterChain.doFilter(servletRequest, servletResponse);
		
//		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String costTime = (System.currentTimeMillis() - startTime) + "ms";
		logWebContext.getMap().put(LogKeyType.COST_TIME.getLogKey(), costTime);
		logWebContext.printLog();
		System.out.println("[ LogWebFilter " + requestURI +" ] cost:" + costTime);
		logWebContext.clear();
		System.out.println("------>>> LogWebFilter end ~~~");
	}
	
	@Override
    public void destroy() {
		System.out.println("------>>> LogWebFilter destroy !!!");
    }

}
