package com.yqh.boot.lock;

/**
 * 默认配置
 * @author Yang.Qinghui
 */
public class RedisLockConfig {
	
	public static final String KEY_PREFIX = "redis_lock_";		// 默认key前缀
	public static final int KEY_EXPRIRE_TIME = 1 * 60 * 60;		// 默认key过期时间，单位：s
	public static final int KEY_LOCK_TIME = 30 * 60 * 1000;		// 默认key同步时间，单位：ms
	public static final int KEY_TRY_TIMES = 3;					// 默认获取key重试次数
	public static final int BLOCK_WAITE_TIME = 1000;			// 默认阻塞时间，单位：ms

}
