package com.yqh.boot.service;

public interface LockService {

	void lockTest2(long time, String logid, Integer seconds) throws Exception;
	
	String lockTest3(long time, String logid, Integer seconds) throws Exception;
	
}
