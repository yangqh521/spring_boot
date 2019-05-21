package com.yqh.boot.security.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class SysRole {

    private String roleName;
    private List<SysPermission> permissionList;

}
