package com.yqh.boot.security.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysUser {

    private Long id;
    private String userName;
    private String password;
    private List<SysRole> roleList;

}
