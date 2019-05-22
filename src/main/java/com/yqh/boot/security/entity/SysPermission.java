package com.yqh.boot.security.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysPermission {

//    private String id;
    private String permissionId;
    private List<String> urlList;

}
