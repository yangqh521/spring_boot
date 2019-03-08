package com.yqh.boot.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yqh.boot.entity.AdverInfo;
import com.yqh.boot.entity.PhoneLogin;
import com.yqh.boot.service.AdverService;
import com.yqh.boot.service.PhoneService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/boot")
public class BootController {

	@Autowired
	private AdverService adverService;
	
	@Autowired
	private PhoneService phoneService;
	
	
	@RequestMapping("/test")
	public void test(){
		
		System.out.println("/boot/test begin ... ");
		
		Map<String,Object> queryMap = new HashMap<>();
		queryMap.put("adverName", "四川");
		List<AdverInfo> adverInfoList = adverService.getAdverInfoList(queryMap);
		System.out.println("adverInfoList >>> " + JSONObject.toJSONString(adverInfoList));
		
		PhoneLogin phoneLogin = new PhoneLogin();
		phoneLogin.setPhone("13141276865");
		phoneLogin.setMappingUserId(0L);
		phoneLogin.setCreateTime(new Date());
		phoneService.insertPhoneLogin(phoneLogin);
		System.out.println("phoneLoginId >>> " + phoneLogin.getPhoneLoginId());
		
		System.out.println("/boot/test end ... ");
	}
	
	
	
}
