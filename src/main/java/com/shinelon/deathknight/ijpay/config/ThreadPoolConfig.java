package com.shinelon.deathknight.ijpay.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/***
 * @author Shinelon
 */
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "delayedQueuePool", destroyMethod = "shutdown")
    public ExecutorService delayedQueuePool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("delayed-queue-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(2, 2, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }

    @Bean(name = "tradeCloseBizPool", destroyMethod = "shutdown")
    public ExecutorService tradeCloseBizPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("trade-close-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(16, 32, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }

    @Bean(name = "billBizPool", destroyMethod = "shutdown")
    public ExecutorService billBizPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("bill-biz-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(16, 32, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }
}
