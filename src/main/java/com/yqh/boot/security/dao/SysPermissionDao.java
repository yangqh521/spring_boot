package com.yqh.boot.security.dao;

import com.yqh.boot.security.entity.SysPermission;
import com.yqh.boot.security.entity.SysRole;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限 <--> 资源
 */
@Repository
public class SysPermissionDao {

    private static Map<String, SysPermission> permissionMap = null;
    private static Map<String, List<SysPermission>> urlMap = null;

    private void initPermissionMap(){

        // 权限
        putPermissionMap("ALL","/","/index", "hello", "/error", "/log/**");

//        putPermissionMap("P_ADMIN", "/security/**");
//        putPermissionMap("P_USER", "/security/**");

        //  测试用
        putPermissionMap("P1", "/security/test1");
        putPermissionMap("P2", "/security/test2");
        putPermissionMap("P3", "/security/test3");
        putPermissionMap("P4", "/security/test4");
        putPermissionMap("P5", "/security/test5");

    }

    private void putPermissionMap(String permissionId, String... urls){
        if(permissionMap == null){
            permissionMap = new HashMap<>();
        }
        if(urlMap == null){
            urlMap = new HashMap<>();
        }

        SysPermission permission = new SysPermission();
        permission.setPermissionId(permissionId);

        List<String> urlList = new ArrayList<>();
        if(urls != null && urls.length > 0){
            for(int i = 0; i < urls.length; i++){
                urlList.add(urls[i]);
                List<SysPermission> permissionList = urlMap.get(urls[i]);
                if(permissionList == null){
                    permissionList = new ArrayList<>();
                }
                permissionList.add(permission);
                urlMap.put(urls[i], permissionList);
            }
        }

        permission.setUrlList(urlList);
        permissionMap.put(permission.getPermissionId(), permission);
    }

    public Map<String, SysPermission> getPermissionMap(){
        if(permissionMap == null){
            initPermissionMap();
        }
        return permissionMap;
    }

    public Map<String, List<SysPermission>> getUrlMap(){
        if(urlMap == null){
            initPermissionMap();
        }
        return urlMap;
    }





}
