package com.yqh.boot.service;

import com.yqh.boot.entity.PhoneLogin;

public interface PhoneService {

	int insertPhoneLogin(PhoneLogin phoneLogin);

    PhoneLogin selectPhoneLoginByPrimaryKey(Integer phoneLoginId);
    
    PhoneLogin getPhoneLogin(PhoneLogin phoneLogin);
}
