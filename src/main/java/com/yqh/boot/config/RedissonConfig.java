package com.yqh.boot.config;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


@Configuration
public class RedissonConfig {
	
//	@Bean(destroyMethod="shutdown")
//    public RedissonClient redissonClient() throws IOException {
//        RedissonClient redissonClient = Redisson.create(
//                Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream()));
//        return redissonClient;
//    }
	
	@Bean
    RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress("123.56.176.83:6379")
                .setDatabase(13)
                .setTimeout(1500);
        serverConfig.setPassword("gx8bzOwHtioVhIkI");
        return Redisson.create(config);
    }

}
