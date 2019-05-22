package com.yqh.boot.security.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 用userDetail里的权限对比有无访问当前url的权限
 * 该类为决策类，决策该用户的request是否有权限访问。
 */
@Slf4j
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * @param authentication UserService中循环添加到GrantedAuthority中的权限信息集合
     * @param object         包含客户端发起的请求的request信息，可以转换为HTTPRequest
     * @param collection     url所需的权限集合
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {

        log.info("decide ~~~~");

        // 判断URL所需的权限集合是否为空，为空则放行
        if (null == collection || collection.size() <= 0) {
            return;
        }

        // 判断是或否有权限
        for (ConfigAttribute configAttribute : collection) {

            //获得所需的权限
            String permissionId = configAttribute.getAttribute();

            //遍历用户拥有的权限与url所需的权限进行对比
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (permissionId.trim().equals(grantedAuthority.getAuthority())){
                    log.info("author success ! permissionId: " + permissionId);
                    return;
                }
            }

        }

        log.info("author fail ！");
        throw new AccessDeniedException("this user do't have permission !!!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}


