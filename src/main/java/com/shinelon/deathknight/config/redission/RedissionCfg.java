package com.shinelon.deathknight.config.redission;

import org.redisson.Redisson;
import org.redisson.api.ExecutorOptions;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.redisson.api.WorkerOptions;
import org.redisson.api.executor.TaskFailureListener;
import org.redisson.api.executor.TaskSuccessListener;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Shinelon
 */
@Configuration
@ConditionalOnProperty(prefix = "enable", name = "redission", havingValue = "true", matchIfMissing = false)
public class RedissionCfg {

    private static final Logger logger = LoggerFactory.getLogger(RedissionCfg.class);
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private String port;
    @Value("${redis.password}")
    private String password;

    @Bean
    public RedissonClient redission() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
//                .setPassword(password)
                .setDatabase(1)
                .setConnectionPoolSize(16)
                .setConnectionMinimumIdleSize(4)
                .setSubscriptionConnectionPoolSize(16)
                .setSubscriptionConnectionMinimumIdleSize(1);
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }


    @Bean(destroyMethod = "")
    public RScheduledExecutorService rScheduledExecutorService(RedissonClient redissonClient, BeanFactory beanFactory) {
        WorkerOptions workerOptions = WorkerOptions.defaults().beanFactory(beanFactory).workers(6).addListener(new TaskSuccessListener() {
            @Override
            public <T> void onSucceeded(String taskId, T result) {
                logger.info("taskId:{} is success resutl:{}", taskId, result);
            }
        }).addListener(new TaskFailureListener() {
            @Override
            public void onFailed(String taskId, Throwable exception) {
                logger.error("{} is failed", taskId);
                logger.error(exception.getMessage(), exception);
            }
        });
        ExecutorOptions executorOptions = ExecutorOptions.defaults().taskRetryInterval(10 * 60, TimeUnit.SECONDS);
        RScheduledExecutorService executorService = redissonClient.getExecutorService("test-redission-scheduled", executorOptions);
        executorService.registerWorkers(workerOptions);
        return executorService;
    }

}
