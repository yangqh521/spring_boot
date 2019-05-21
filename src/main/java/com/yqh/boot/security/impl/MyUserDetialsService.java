package com.yqh.boot.security.impl;

import com.alibaba.fastjson.JSONObject;
import com.yqh.boot.security.dao.SysUserDao;
import com.yqh.boot.security.entity.SysPermission;
import com.yqh.boot.security.entity.SysRole;
import com.yqh.boot.security.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MyUserDetialsService implements UserDetailsService {

    @Autowired
    SysUserDao sysUserDao;

    @Override
    public UserDetails loadUserByUsername(String userName) {

        log.info("userName : " + userName);

        // 校验用户名称
        SysUser user = sysUserDao.findByUserName(userName);
        if(user == null) {
            return null;
        }

        log.info("sysuser : " + JSONObject.toJSONString(user));

        // 用户 -> 角色 -> 权限
        List<GrantedAuthority> grantedAuthoritieList = new ArrayList<>();

        // 获取用户的角色集合
        List<SysRole> roleList = user.getRoleList();
        //遍历角色集合，并获取每个角色拥有的权限
        for (SysRole role : roleList) {
            List<SysPermission> permissionList = role.getPermissionList();
            for (SysPermission permission :permissionList) {
                // 为每个授权中心对象写入权限名
                grantedAuthoritieList.add(new SimpleGrantedAuthority(permission.getPermissionName()));
            }
        }

        log.info("grantedAuthoritieList : " + JSONObject.toJSONString(grantedAuthoritieList));

        /**
         * 此处的user是springsecurity中的一个实现了UserDetails接口的user类
         */
        return new User(user.getUserName(), user.getPassword(), grantedAuthoritieList);
    }
}

