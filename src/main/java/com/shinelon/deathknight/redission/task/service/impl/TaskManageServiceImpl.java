package com.shinelon.deathknight.redission.task.service.impl;

import com.shinelon.deathknight.redission.task.bean.TaskBean;
import com.shinelon.deathknight.redission.task.bean.TaskDetailBean;
import com.shinelon.deathknight.redission.task.job.DetailJob;
import com.shinelon.deathknight.redission.task.service.TaskManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.redisson.api.CronSchedule;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RScheduledFuture;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author shinelon
 */
@Service
@ConditionalOnBean(value = RedissonClient.class)
@Slf4j
public class TaskManageServiceImpl implements TaskManageService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RScheduledExecutorService rScheduledExecutorService;

    @Override
    public List<TaskBean> getTaskList() {
        return Arrays.asList(getMockTask("1"), getMockTask("2"), getMockTask("3"));
    }


    @Override
    public void startScheduler(List<TaskBean> todoList) {
        // check repeat
        for (int i = 0; i < todoList.size(); i++) {
            int nextInt = RandomUtils.nextInt(1, 30);
            TaskBean taskBean = todoList.get(i);
            String cron = nextInt + "/30 * * * * ? ";
            RScheduledFuture<?> job = rScheduledExecutorService
                    .schedule(new DetailJob(taskBean), CronSchedule.of(cron));
            String taskId = job.getTaskId();
            log.info("taskJobId :{},corn:{}", taskId, cron);
        }
    }

    private TaskBean getMockTask(String idStr) {
        String id = RandomUtils.nextInt(0, 10) + "";
        return TaskBean.builder()
                .taskId(Optional.ofNullable(idStr).orElse(id))
                .taskContent(id + "_task")
                .workerCount(RandomUtils.nextInt(1, 5))
                .scheduleJobId("")
                .scheduleJobStatus("init")
                .details(Arrays.asList(getMockDetail(), getMockDetail()))
                .build();
    }

    private TaskDetailBean getMockDetail() {
        String random = RandomUtils.nextInt() + "";
        return TaskDetailBean.builder().detailId(random).content(random + "_detail").build();
    }
}
