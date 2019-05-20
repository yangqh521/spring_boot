package com.yqh.boot.log.aop;

import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yqh.boot.log.enu.LogKeyType;
import com.yqh.boot.log.enu.LogNameType;


public class LogWebContext {
	
	
	private static final ThreadLocal<Map<String, String>> cache = new ThreadLocal<Map<String, String>>() {
		@Override
		protected Map<String, String> initialValue() {
			return Maps.newLinkedHashMap();
		}
	};

	private static LogWebContext instance = new LogWebContext();

    public static LogWebContext getInstance(){
    	return instance;
    }

    private LogWebContext(){}
    
    public Map<String,String> getLogMap(){
    	return cache.get();
    }
    
    public String getLog(String key){
    	return cache.get().get(key);
    }
    
    public void setLogName(LogNameType LogNameType){
    	cache.get().put(LogKeyType.LOG_NAME.getLogKey(), LogNameType.getLogName());
    }
    
    public void putLog(String key, String value){
    	cache.get().put(key, value);
    }
    
    public void printLog(){
    	Map<String, String> map = cache.get();
    	String logName = map.get(LogKeyType.LOG_NAME.getLogKey());
    	cache.remove();
    	map.put(LogKeyType.LOG_NAME.getLogKey(), null);
    	map.put(LogKeyType.START_TIME.getLogKey(), null);
    	if(logName == null){
    		return;
    	}
    	if(LogNameType.CONSOLE.getLogName().equals(logName)){
    		Logger logger = Logger.getLogger(LogWebContext.class);
    		logger.info(JSONObject.toJSON(map));
    		return;
    	}
    	Logger logger = Logger.getLogger(logName);
    	logger.info(JSONObject.toJSON(map));
		return;
    }
    
    public void clear() {
        cache.remove();
    }

	
}
