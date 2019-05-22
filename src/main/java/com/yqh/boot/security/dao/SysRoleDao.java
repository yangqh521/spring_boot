package com.yqh.boot.security.dao;


import com.yqh.boot.security.entity.SysPermission;
import com.yqh.boot.security.entity.SysRole;
import com.yqh.boot.security.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色 <--> 权限
 */
@Repository
public class SysRoleDao {

    @Autowired
    SysPermissionDao sysPermissionDao;


    private static Map<String, SysRole> roleMap = null;

    private void initRoleMap(){

        putRoleMap("admin", "ROLE_ADMIN", "ALL", "P1", "P2", "P3", "P4");
        putRoleMap("user", "ROLE_USER", "ALL", "P3", "P4", "P5");


    }

    private void putRoleMap(String roleId, String roleName, String... permissionIds){

        if(roleMap == null){
            roleMap = new HashMap<>();
        }

        Map<String, SysPermission> permissionMap = sysPermissionDao.getPermissionMap();

        List<SysPermission> permissionList = new ArrayList<>();
        if(permissionIds != null && permissionIds.length > 0){
            for(int i= 0; i < permissionIds.length; i++){
                SysPermission permission = permissionMap.get(permissionIds[i]);
                if(permission != null){
                    permissionList.add(permission);
                }
            }
        }

        SysRole sysRole = new SysRole();
        sysRole.setRoleId(roleId);
        sysRole.setRoleName(roleName);
        sysRole.setPermissionList(permissionList);
        roleMap.put(sysRole.getRoleId(), sysRole);

    }



    public Map<String, SysRole> getRoleMap(){
        if(roleMap == null){
            initRoleMap();
        }
        return roleMap;
    }





}
