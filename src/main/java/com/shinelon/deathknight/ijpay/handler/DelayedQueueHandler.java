package com.shinelon.deathknight.ijpay.handler;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/***
 * @author Shinelon
 */
@Component
@Slf4j
public class DelayedQueueHandler {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 重启后需要唤醒延迟队列
     *
     * @param queueName
     * @param <T>
     */
    public <T> void initQueue(String queueName) {
        RBlockingDeque<T> blockingDeque = redissonClient.getBlockingDeque(queueName);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        int size = delayedQueue.size();
        log.info("delayedQueue.init,size:{}", size);

    }

    public <T> void addQueue(String queueName, T t, long delay, TimeUnit timeUnit) {
        log.info("delayedQueueHandler.addQueue:{},delay:{},timeUnit:{}", t, delay, timeUnit);
        RBlockingDeque<T> blockingDeque = redissonClient.getBlockingDeque(queueName);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        delayedQueue.offer(t, delay, timeUnit);
    }

    public <T> T takeQueue(String queueName) {
        RBlockingDeque<T> blockingDeque = redissonClient.getBlockingDeque(queueName);
        try {
            return blockingDeque.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


}
