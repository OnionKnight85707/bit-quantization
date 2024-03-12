package com.mzwise.common.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {

        Config config = new Config();

        config.useSingleServer().setAddress(
                new StringBuilder().append("redis://").append(host)
                        .append(":").append(port).toString()
        );

        if (StringUtils.isNotEmpty(password)) {
            config.useSingleServer().setPassword(password);
            config.useSingleServer().setConnectionMinimumIdleSize(1);
        }

        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
