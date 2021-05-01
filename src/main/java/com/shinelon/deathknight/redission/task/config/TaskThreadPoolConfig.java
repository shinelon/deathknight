package com.shinelon.deathknight.redission.task.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author shinelon
 */
@Configuration
@ConditionalOnProperty(prefix = "enable", name = "redission", havingValue = "true", matchIfMissing = false)
public class TaskThreadPoolConfig {

//    @Bean("task-boss")
//    public ThreadPoolTaskExecutor bossExecutor(){
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(4);
//        executor.setKeepAliveSeconds(30);
//        executor.setQueueCapacity(1024);
//        executor.setThreadNamePrefix("task-boss-");
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        return executor;
//    }

    @Bean("task-worker")
    public ThreadPoolTaskExecutor workerExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setKeepAliveSeconds(30);
        executor.setQueueCapacity(1024);
        executor.setThreadNamePrefix("task-worker-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
