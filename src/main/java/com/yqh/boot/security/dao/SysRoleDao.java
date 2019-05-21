package com.yqh.boot.security.dao;


import com.yqh.boot.security.entity.SysPermission;
import com.yqh.boot.security.entity.SysRole;
import com.yqh.boot.security.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色 --> 权限
 */
@Repository
public class SysRoleDao {

    Map<String, SysRole> roleMap = null;

    private void initRoleMap(){

        roleMap = new HashMap<>();

        // 权限
        SysPermission p1 = new SysPermission();
        p1.setPermissionName("/security/test1");

        SysPermission p2 = new SysPermission();
        p2.setPermissionName("/security/test2");

        SysPermission p3 = new SysPermission();
        p3.setPermissionName("/security/test3");

        SysPermission p4 = new SysPermission();
        p4.setPermissionName("/security/test4");

        SysPermission p5 = new SysPermission();
        p5.setPermissionName("/security/test5");


        // 角色
        SysRole role_admin = new SysRole();
        role_admin.setRoleName("ROLE_ADMIN");
        List<SysPermission> adminPerList = new ArrayList<>();
        adminPerList.add(p1);
        adminPerList.add(p2);
        adminPerList.add(p3);
        adminPerList.add(p4);
        role_admin.setPermissionList(adminPerList);
        roleMap.put(role_admin.getRoleName(),role_admin);

        SysRole role_user = new SysRole();
        role_user.setRoleName("ROLE_USER");
        List<SysPermission> userPerList = new ArrayList<>();
        userPerList.add(p3);
        userPerList.add(p4);
        userPerList.add(p5);
        role_user.setPermissionList(userPerList);
        roleMap.put(role_user.getRoleName(),role_user);


    }

    public Map<String, SysRole> getRoleMap(){
        if(roleMap == null){
            initRoleMap();
        }
        return roleMap;
    }





}
