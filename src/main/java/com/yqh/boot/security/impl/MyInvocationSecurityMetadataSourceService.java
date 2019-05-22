package com.yqh.boot.security.impl;

import com.alibaba.fastjson.JSONObject;
import com.yqh.boot.security.dao.SysPermissionDao;
import com.yqh.boot.security.entity.SysPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 权限校验
 * 加载所有权限
 *  根据当前获取权限
 *
 *  该类用于加载权限表中的url信息，并和request的url进行对比，有匹配则将该URL所需要的权限返回给decide()方法，不存在则返回空
 */
@Slf4j
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysPermissionDao sysPermissionDao;

    private Map<String, Collection<ConfigAttribute>> map = null;

    //在demo启动第一个用户登陆后，加载所有权限进map
    public void loadResourceDefine() {

        log.info("loadResourceDefine ~~~~");

        map = new HashMap<>();

        Map<String, List<SysPermission>> urlMap = sysPermissionDao.getUrlMap();

        for (String url : urlMap.keySet()) {

            Collection<ConfigAttribute> configAttributeArray = new ArrayList<>();

            List<SysPermission> permissionList = urlMap.get(url);
            for (SysPermission permission : permissionList) {
                //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
                ConfigAttribute configAttribute =  new SecurityConfig(permission.getPermissionId());
                configAttributeArray.add(configAttribute);
            }

            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value
            map.put(url, configAttributeArray);

        }

        log.info("map:" + JSONObject.toJSONString(map));

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        if(map ==null) {
            loadResourceDefine();
        }

        log.info("getAttributes ~~~~");

        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        //遍历权限表中的url
        for (String url : map.keySet()) {

            /**
             * 使用/** 或 ** 的模式值被视为通用匹配，这将匹配任何请求。
             * 以/** 结尾的模式（并且没有其他通配符）比如 /aaa/** 的模式将匹配/aaa，/aaa/和任何子目录
             *
             *  ANT方式的通配符有三种：
             *     ?（匹配任何单字符），*（匹配0或者任意数量的字符），**（匹配0或者更多的目录）
             */
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);

            //与request对比，符合则说明权限表中有该请求的URL
            if(matcher.matches(request)) {

                log.info("matches success ! url:" + url + ", array:" + JSONObject.toJSONString(map.get(url)));

                return map.get(url);
            }

        }

        log.info("matches fail ! uri:" + request.getRequestURI());
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}


