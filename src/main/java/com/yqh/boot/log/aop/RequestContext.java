package com.yqh.boot.log.aop;


import javax.servlet.http.HttpServletRequest;

public class RequestContext {
    /**
     * 存储传入参数
     */
    private static ThreadLocal<HttpServletRequest> cache = new ThreadLocal<HttpServletRequest>() {
        @Override
        public HttpServletRequest initialValue() {
            return null;
        }
    };

    /**
     * 单例
     */
    private static RequestContext instance = new RequestContext();

    public static RequestContext getInstance() {
        return instance;
    }

    private RequestContext() {
    }

    /**
     * 初始化
     *
     * @param request
     */
    public void setRequest(HttpServletRequest request) {
        cache.set(request);
    }

    public HttpServletRequest getRequest() {
        return cache.get();
    }

    /**
     * 防止内存泄露，线程池复用
     * Thread Ref -> Thread -> ThreaLocalMap -> Entry(key -> ThreadLocal WeakReference -> null) -> value
     */
    public void clear() {
        cache.remove();
    }
}