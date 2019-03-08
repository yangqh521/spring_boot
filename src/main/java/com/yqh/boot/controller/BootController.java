package com.yqh.boot.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
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
	
	@Resource
	private Environment env;
	
	
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
	
	@RequestMapping("/testEnv")
	public void testEnv(){
		System.out.println("/boot/testEnv begin ... ");
		String[] activeProfiles = env.getActiveProfiles();
		for (String activeProfile : activeProfiles) {
			System.out.println("activeProfiles >>> " + activeProfile);
		}
		String[] defaultProfiles = env.getDefaultProfiles();
		for (String defaultProfile : defaultProfiles) {
			System.out.println("activeProfiles >>> " + defaultProfile);
		}
		
		String url = env.getProperty("spring.datasource.dsp.jdbc-url");
		String username = env.getProperty("spring.datasource.dsp.username");
		String password = env.getProperty("spring.datasource.dsp.password");
		String driverClassName = env.getProperty("spring.datasource.dsp.driver-class-name");
		
		System.out.println("dsp url >>> " + url);
		System.out.println("dsp username >>> " + username);
		System.out.println("dsp password >>> " + password);
		System.out.println("dsp driverClassName >>> " + driverClassName);
		
		System.out.println("/boot/testEnv end ... ");
	}
	
}
