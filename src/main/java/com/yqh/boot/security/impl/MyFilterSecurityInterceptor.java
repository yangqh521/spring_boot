package com.yqh.boot.security.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;


@Slf4j
@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {


    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    // 设置决策器
    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("---- execute MyFilterSecurityInterceptor enter");

        FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
        invoke(filterInvocation);

        log.info("---- execute MyFilterSecurityInterceptor out");
    }


    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {

        // filterInvocation里面有一个被拦截的url
        // 里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
        // 再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);

        //执行下一个拦截器
        try {

            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {

            super.afterInvocation(token, null);
        }
    }


    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;

    }

    // 添加判断url所需的权限类
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        log.info("obtainSecurityMetadataSource ~~~");
        return this.securityMetadataSource;
    }
}
