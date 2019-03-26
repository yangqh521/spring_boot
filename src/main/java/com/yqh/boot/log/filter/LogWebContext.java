package com.yqh.boot.log.filter;

import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yqh.boot.log.LogKeyType;
import com.yqh.boot.log.LogNameType;


public class LogWebContext {
	
	
	private static final ThreadLocal<Map<String, Object>> cache = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return Maps.newLinkedHashMap();
		}
	};

	private static LogWebContext instance = new LogWebContext();

    public static LogWebContext getInstance(){
    	return instance;
    }

    private LogWebContext(){}
    
    public void clear() {
        cache.remove();
    }
    
    public Map<String,Object> getMap(){
    	return cache.get();
    }
    
    @SuppressWarnings("unchecked")
	public Map<String,Object> getLogMap(){
    	Object object = getMap().get(LogKeyType.LOG_MAP.getLogKey());
    	if(object != null)	return (Map<String,Object>)object;
    	return Maps.newLinkedHashMap();
    }
    
    public Object getLog(String key){
    	Map<String, Object> logMap = this.getLogMap();
    	if(logMap == null)	return null;
    	if(logMap.get(key) != null)	return logMap.get(key);
    	return null;
    }
    
    public void setLogName(LogNameType LogNameType){
    	getMap().put(LogKeyType.LOG_NAME.getLogKey(), LogNameType.getLogName());
    }
    
    public void putLog(String key, Object value){
    	Map<String, Object> logMap = getLogMap();
    	logMap.put(key, value);
    	getMap().put(LogKeyType.LOG_MAP.getLogKey(), logMap);
    }
    
    public void printLog(){
    	Map<String, Object> map = getMap();
    	if(map.get(LogKeyType.LOG_NAME.getLogKey()) == null)	return;
    	String logName = (String) map.get(LogKeyType.LOG_NAME.getLogKey());
    	if(LogNameType.CONSOLE.getLogName().equals(logName)){
    		Logger logger = Logger.getLogger(LogWebContext.class);
    		logger.info(JSONObject.toJSONString(map));
    		return;
    	}
    	Logger logger = Logger.getLogger(logName);
    	logger.info(JSONObject.toJSONString(map));
		return;
    }
    

	
}
