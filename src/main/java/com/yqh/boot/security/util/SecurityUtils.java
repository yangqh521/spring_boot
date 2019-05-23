package com.yqh.boot.security.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Authentication是spring security包中的接口，直接继承自Principal类，而Principal是位于java.security包中的
 * 由这个顶级接口，我们可以得到用户拥有的权限信息列表，密码，用户细节信息，用户身份信息，认证信息
 *
 *      getAuthorities()，权限信息列表，默认是GrantedAuthority接口的一些实现类，通常是代表权限信息的一系列字符串
 *      getCredentials()，密码信息，用户输入的密码字符串，在认证过后通常会被移除，用于保障安全
 *      getDetails()，细节信息，web应用中的实现接口通常为 WebAuthenticationDetails，它记录了访问者的ip地址和sessionId的值
 *      getPrincipal()，最重要的身份信息，大部分情况下返回的是UserDetails接口的实现类
 */
public class SecurityUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    public static Collection<? extends GrantedAuthority> getAllPermission(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities;
    }

    public static boolean hasPermission(String permission){
        if(StringUtils.isEmpty(permission)){
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasPermission = false;
        for(GrantedAuthority grantedAuthority : authorities){
            String authority = grantedAuthority.getAuthority();
            if(authority.equals(permission)){
                hasPermission =true;
            }
        }
        return hasPermission;
    }


    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }


    public static void logout(){
        SecurityContextHolder.clearContext();
    }
}
