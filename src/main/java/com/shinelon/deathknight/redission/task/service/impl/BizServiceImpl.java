package com.shinelon.deathknight.redission.task.service.impl;

import com.shinelon.deathknight.redission.task.bean.TaskDetailBean;
import com.shinelon.deathknight.redission.task.service.BizService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author shinelon
 */
@Service
@ConditionalOnBean(value = RedissonClient.class)
@Slf4j
public class BizServiceImpl implements BizService {

    @Autowired
    private RedissonClient redisson;

    @Override
    @Async("task-worker")
    public void bizExecute(String taskId, TaskDetailBean taskDetailBean) {
        RPermitExpirableSemaphore semaphore = redisson.getPermitExpirableSemaphore(taskId);
        try {
            String semaphoreId = semaphore.acquire(30, TimeUnit.SECONDS);
            if (semaphoreId != null) {
                try {
                    log.info("biz...logic...");
                    log.info("check status and repate");
                    log.info("taskId:{},taskDetailBean:{}", taskId, taskDetailBean);
                } finally {
                    semaphore.release(semaphoreId);
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
