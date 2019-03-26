package com.yqh.boot.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
	
	@Value("${spring.redis.database}")
    private Integer database;
	
	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.port}")
	private Integer port;
	
	@Value("${spring.redis.password}")
	private String password;
	
	@Value("${spring.redis.timeout}")
	private Integer timeout;

	/**
	 * redisson
	 * @return
	 */
	@Bean
    RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(host+ ":" + port)
                .setDatabase(database)
                .setTimeout(timeout);
        serverConfig.setPassword(password);
        return Redisson.create(config);
    }
	
}
