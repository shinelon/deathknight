package com.shinelon.deathknight.redission;

import org.redisson.api.RBoundedBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@ConditionalOnBean(value = RedissonClient.class)
public class DelayedQueueService {

    private static final Logger logger = LoggerFactory.getLogger(DelayedQueueService.class);

    @Autowired
    private RedissonClient redissonClient;


    public void delayQueue() {
        RBoundedBlockingQueue<String> blockingFairQueue = redissonClient.getBoundedBlockingQueue("delay_queue");
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.offer("offerValue111", 15, TimeUnit.SECONDS);
        logger.info("offer a value");
        try {
            String take = blockingFairQueue.take();
            logger.info("take a value:{}", take);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
