package com.yqh.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yqh.boot.common.AppResponse;
import com.yqh.boot.log.enu.LogNameType;
import com.yqh.boot.log.filter.LogWebContext;

@RestController
@RequestMapping("/log")
public class LogAction {


	@RequestMapping("/test1")
	public AppResponse logTest1(String username,String password){
		System.out.println("--->> log test1 start <<---");
		
		LogWebContext logWebContext = LogWebContext.getInstance();
		
		AppResponse resp = new AppResponse();
		
		logWebContext.putLog("username", username);
		logWebContext.putLog("password", password);
		logWebContext.putLog("timestamp", System.currentTimeMillis());
		
		logWebContext.setLogName(LogNameType.CONSOLE);
		
		System.out.println("--->> log test1 end <<---");
		resp.setData("adcbefg");
		resp.setCode(0);
		return resp;
	}
	
}
