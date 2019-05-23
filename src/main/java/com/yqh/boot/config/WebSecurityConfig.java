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


    /**
     * 密码加密
     *
     * BCryptPasswordEncoder相关知识：
     * 用户表的密码通常使用MD5等不可逆算法加密后存储，为防止彩虹表破解更会先使用一个特定的字符串（如域名）加密，然后再使用一个随机的salt（盐值）加密。
     * 特定字符串是程序代码中固定的，salt是每个密码单独随机，一般给用户表加一个字段单独存储，比较麻烦。
     * BCrypt算法将salt随机并混入最终加密后的密码，验证时也无需单独提供之前的salt，从而无需单独处理salt问题。
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 添加 UserDetailsService，实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetialsService)
                .passwordEncoder(passwordEncoder());
    }

    //配置加密
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetialsService).passwordEncoder(new PasswordEncoder() {
//            //加密
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return MD5Util.encode((String) rawPassword);
//            }
//
//            //解密,前者是输入的密码,后者是数据库查询的密码
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return encodedPassword.equals(MD5Util.encode((String) rawPassword));
//            }
//        });
//    }


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

