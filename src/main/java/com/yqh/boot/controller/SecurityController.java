package com.yqh.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.yqh.boot.common.AppResponse;
import com.yqh.boot.security.util.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @RequestMapping("/test1")
    public AppResponse test1(){
        AppResponse appResponse = new AppResponse();
        appResponse.setData(getSecurity());
        return appResponse;
    }

    @RequestMapping("/test2")
    public AppResponse test2(){
        AppResponse appResponse = new AppResponse();
        appResponse.setData(getSecurity());
        return appResponse;
    }

    @RequestMapping("/test3")
    public AppResponse test3(){
        AppResponse appResponse = new AppResponse();
        appResponse.setData(getSecurity());
        return appResponse;
    }

    @RequestMapping("/test4")
    public AppResponse test4(){
        AppResponse appResponse = new AppResponse();
        appResponse.setData(getSecurity());
        return appResponse;
    }

    @RequestMapping("/test5")
    public AppResponse test5(){
        AppResponse appResponse = new AppResponse();
        appResponse.setData(getSecurity());
        return appResponse;
    }


    private JSONObject getSecurity(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", SecurityUtils.getUser());
        jsonObject.put("authentication", SecurityUtils.getAuthentication());
        jsonObject.put("allPermission", SecurityUtils.getAllPermission());
        return jsonObject;
    }


}
