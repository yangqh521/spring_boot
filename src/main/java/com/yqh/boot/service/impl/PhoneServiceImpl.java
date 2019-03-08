package com.yqh.boot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqh.boot.dao.site.PhoneLoginMapper;
import com.yqh.boot.entity.PhoneLogin;
import com.yqh.boot.service.PhoneService;


@Service
public class PhoneServiceImpl implements PhoneService {

	
	@Autowired
	private PhoneLoginMapper phoneLoginMapper;

	@Override
	public int insertPhoneLogin(PhoneLogin phoneLogin) {
		return phoneLoginMapper.insertPhoneLogin(phoneLogin);
	}

	@Override
	public PhoneLogin selectPhoneLoginByPrimaryKey(Integer phoneLoginId) {
		return phoneLoginMapper.selectPhoneLoginByPrimaryKey(phoneLoginId);
	}

	@Override
	public PhoneLogin getPhoneLogin(PhoneLogin phoneLogin) {
		return phoneLoginMapper.getPhoneLogin(phoneLogin);
	}
	
}
