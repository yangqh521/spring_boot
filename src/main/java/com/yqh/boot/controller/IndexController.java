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

    // 默认走login页面
    @RequestMapping("/loginUser")
    public String login(HttpServletRequest request, HttpServletResponse response,
                        String username, String password) {

//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//
//        try{
//            //使用SpringSecurity拦截登陆请求 进行认证和授权
//            Authentication authenticate = myAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
//
//            SecurityContextHolder.getContext().setAuthentication(authenticate);
//            //使用redis session共享
//            HttpSession session = request.getSession();
//            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
//        }catch (Exception e){
//            e.printStackTrace();
//            return "redirect:login-error?error=2";
//        }


        return "redirect:index";

    }

}
