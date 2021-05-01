package com.shinelon.deathknight.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

/***
 * @author Shinelon
 */
@Configuration
@ConditionalOnProperty(prefix = "enable", name = "redis", havingValue = "true", matchIfMissing = false)
public class RedisConfig {

    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private String port;
    @Value("${redis.password}")
    private String password;

    @Bean
    @Primary
    public RedisProperties redisProperties() {
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost(host);
        redisProperties.setPort(Integer.parseInt(port));
//        redisProperties.setPassword(password);
        redisProperties.setTimeout(Duration.ofSeconds(10));
        RedisProperties.Pool pool = new RedisProperties.Pool();
        pool.setMaxActive(16);
        pool.setMaxIdle(16);
        pool.setMinIdle(8);
        pool.setMaxWait(Duration.ofSeconds(5));
        pool.setTimeBetweenEvictionRuns(Duration.ofSeconds(60));
        redisProperties.getLettuce().setPool(pool);
        return redisProperties;
    }

}
