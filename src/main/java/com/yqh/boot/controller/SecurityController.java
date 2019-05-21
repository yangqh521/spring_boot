package com.yqh.boot.controller;

import com.yqh.boot.common.AppResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @RequestMapping("/test1")
    public AppResponse test1(){
        AppResponse appResponse = new AppResponse();
        appResponse.setData("/security/test1 ~~~ ");
        return appResponse;
    }

    @RequestMapping("/test2")
    public AppResponse test2(){
        AppResponse appResponse = new AppResponse();
        appResponse.setData("/security/test2 ~~~ ");
        return appResponse;
    }

    @RequestMapping("/test3")
    public AppResponse test3(){
        AppResponse appResponse = new AppResponse();
        appResponse.setData("/security/test3 ~~~ ");
        return appResponse;
    }

    @RequestMapping("/test4")
    public AppResponse test4(){
        AppResponse appResponse = new AppResponse();
        appResponse.setData("/security/test4 ~~~ ");
        return appResponse;
    }

    @RequestMapping("/test5")
    public AppResponse test5(){
        AppResponse appResponse = new AppResponse();
        appResponse.setData("/security/test5 ~~~ ");
        return appResponse;
    }

}
