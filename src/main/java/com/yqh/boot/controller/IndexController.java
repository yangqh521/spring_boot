package com.yqh.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        log.info("enter index.html ~~~");
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        log.info("enter hello.html ~~~");
        return "hello";
    }

    // 默认走login页面
    @RequestMapping("/login")
    public String login() {
        log.info("enter login.html ~~~");
        return "login";
    }


}
