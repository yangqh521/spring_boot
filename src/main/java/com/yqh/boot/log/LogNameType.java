package com.yqh.boot.log;


public enum LogNameType {
	
	CONSOLE("console"),
	CASH_SEND("cashRedPackageSend");
	
	private String logName;
	
	LogNameType(String logName) {
		this.logName = logName;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}
	
	
	

}
