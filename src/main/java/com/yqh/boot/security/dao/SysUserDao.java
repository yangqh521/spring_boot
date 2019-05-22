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
 * 用户 <--> 角色
 */
@Repository
public class SysUserDao {

    @Autowired
    SysRoleDao sysRoleDao;

    private static Map<String, SysUser> userMap = null;

    private void initUserMap(){

        putUserMap("yangqh", "123456", "admin");
        putUserMap("wenwen", "123456", "user");

    }

    private void putUserMap(String userName, String password, String... roleIds){

        if(userMap == null){
            userMap = new HashMap<>();
        }

        Map<String, SysRole> roleMap = sysRoleDao.getRoleMap();

        List<SysRole> roleList = new ArrayList<>();
        if(roleIds != null && roleIds.length > 0){
            for(int i = 0; i < roleIds.length; i ++){
                SysRole role = roleMap.get(roleIds[i]);
                if(role != null){
                    roleList.add(role);
                }
            }
        }

        SysUser user = new SysUser();
        user.setUserName(userName);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRoleList(roleList);

        userMap.put(user.getUserName(), user);

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
