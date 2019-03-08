package com.yqh.boot.dao.site;

import org.apache.ibatis.annotations.Mapper;
import com.yqh.boot.entity.PhoneLogin;


@Mapper
public interface PhoneLoginMapper {

    int insertPhoneLogin(PhoneLogin phoneLogin);

    PhoneLogin selectPhoneLoginByPrimaryKey(Integer phoneLoginId);
    
    PhoneLogin getPhoneLogin(PhoneLogin phoneLogin);
    
}