package com.yqh.boot.common;

public class AppResponse {

	private int code = 0;
	
	private Object data;
	
	private String fail;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getFail() {
		return fail;
	}
	public void setFail(String fail) {
		this.fail = fail;
	}
	
	
	
	
	
}
