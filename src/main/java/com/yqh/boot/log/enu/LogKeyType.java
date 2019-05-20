package com.yqh.boot.log.enu;

import lombok.Getter;

public enum LogKeyType {
	
	LOG_NAME("LOG_NAME"),
	LOG_MAP("LOG_MAP"),
	START_TIME("START_TIME"),
	END_TIME("END_TIME"),
	COST_TIME("COST_TIME"),
	URI("URI"),
	PAMRAMS("PAMRAMS"),
	IS_SUCCESS("IS_SUCCESS");
	
	private String logKey;
	
	LogKeyType(String logKey) {
		this.logKey = logKey;
	}

	public String getLogKey() {
		return logKey;
	}

	public void setLogKey(String logKey) {
		this.logKey = logKey;
	}
	

}
