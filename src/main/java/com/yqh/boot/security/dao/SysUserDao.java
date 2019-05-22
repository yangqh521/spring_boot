package com.yqh.boot.security.dao;

import com.yqh.boot.security.entity.SysPermission;
import com.yqh.boot.security.entity.SysRole;
import com.yqh.boot.security.entity.SysUser;
import com.yqh.boot.security.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 --> 角色
 */
@Repository
public class SysUserDao {

    @Autowired
    SysRoleDao sysRoleDao;

    private static Map<String, SysUser> userMap = null;

    private void initUserMap(){

        userMap = new HashMap<>();

        Map<String, SysRole> roleMap = sysRoleDao.getRoleMap();

        // 用户
        SysUser user1 = new SysUser();
        user1.setUserName("yangqh");
        user1.setPassword(new BCryptPasswordEncoder().encode("123456"));
        List<SysRole> user1RoleList = new ArrayList<>();
        user1RoleList.add(roleMap.get("ROLE_ADMIN"));
        user1.setRoleList(user1RoleList);
        userMap.put(user1.getUserName(), user1);


        SysUser user2 = new SysUser();
        user2.setUserName("wenwen");
        user2.setPassword(new BCryptPasswordEncoder().encode("123456"));
        List<SysRole> user2RoleList = new ArrayList<>();
        user2RoleList.add(roleMap.get("ROLE_USER"));
        user2.setRoleList(user2RoleList);
        userMap.put(user2.getUserName(), user2);


    }

    public Map<String, SysUser> getUserMap(){
        if(userMap == null){
            initUserMap();
        }
        return userMap;
    }


    public SysUser findByUserName(String userName) {
        return getUserMap().get(userName);
    }




}
