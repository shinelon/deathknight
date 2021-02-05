package com.shinelon.deathknight.config.mdc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/***
 * PoolTaskConfig
 * @author Shinelon
 */
@Configuration
@EnableAsync
@Slf4j
public class PoolTaskConfig {
    @Bean("async-exec")
    public TaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setKeepAliveSeconds(5);
        executor.setQueueCapacity(1024);
        executor.setThreadNamePrefix("async-exec-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setTaskDecorator(new MdcTaskDecorator());
        executor.initialize();
        log.info("asyncExecutor.init...");
        return executor;
    }
}
