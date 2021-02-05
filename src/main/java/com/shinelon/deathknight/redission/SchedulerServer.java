package com.shinelon.deathknight.redission;

import org.redisson.api.CronSchedule;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RScheduledFuture;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnBean(value = RedissonClient.class)
public class SchedulerServer {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerServer.class);

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RScheduledExecutorService rScheduledExecutorService;

    public Set<String> listJob() {
        Set<String> taskIds = rScheduledExecutorService.getTaskIds();
        return taskIds;
    }

    public void cannelTask(String taskId) {
        rScheduledExecutorService.cancelTask(taskId);
    }

    public void addJob() {
        rScheduledExecutorService.schedule(new TaskJob("job1"), 10,
                TimeUnit.SECONDS);
        rScheduledExecutorService.schedule(new TaskJob("job1"), 30,
                TimeUnit.SECONDS);
        RScheduledFuture<?> job3 = rScheduledExecutorService.schedule(new TaskJob("job3"), CronSchedule.of("0/10 * * * * ? "));
        String taskId = job3.getTaskId();
        logger.info("tasId :{}", taskId);
    }

    public void deleteScheduled() {
        rScheduledExecutorService.delete();
    }
}
