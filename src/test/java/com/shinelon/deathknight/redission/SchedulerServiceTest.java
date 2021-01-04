package com.shinelon.deathknight.redission;


import com.shinelon.deathknight.DeathknightApplicationTests;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SchedulerServiceTest extends DeathknightApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceTest.class);
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private SchedulerServer schedulerServer;


    @Test
    public void test() {
        RBucket<String> bucket = redissonClient.getBucket("test-redission-key");
//        bucket.set("test-redission-value");
        String result = bucket.get();
        logger.info(result);


    }

    @Test
    public void addJob() {
        schedulerServer.addJob();
    }

}
