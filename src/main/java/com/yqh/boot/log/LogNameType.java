package com.yqh.boot.log;

import lombok.Getter;

@Getter
public enum LogNameType {
	
	CONSOLE("console");

	private String logName;
	
	LogNameType(String logName) {
		this.logName = logName;
	}
	
	
	

}
