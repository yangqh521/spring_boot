package com.yqh.boot.config;


import com.yqh.boot.security.impl.MyUserDetialsService;
import com.yqh.boot.security.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetialsService userDetialsService;

    //配置加密
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetialsService).passwordEncoder(new PasswordEncoder() {
            //加密
            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }

            //解密,前者是输入的密码,后者是数据库查询的密码
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String) rawPassword));
            }
        });
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //自定义登陆页面
            .formLogin().loginPage("/login")
            //登陆成功后跳转的页面
            .defaultSuccessUrl("/index")
            //登陆失败或无权限跳转页面
            .failureUrl("/error")
            .permitAll()
            //其他所有页面必须验证后才可以访问
            .and().authorizeRequests().anyRequest().authenticated()
            //不加上不验证。不知道为什么
            .and().csrf().disable();

    }






}

