package com.shinelon.deathknight.redission.task.job;

import com.shinelon.deathknight.redission.task.bean.TaskBean;
import com.shinelon.deathknight.redission.task.bean.TaskDetailBean;
import com.shinelon.deathknight.redission.task.service.BizService;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RedissonClient;
import org.redisson.api.annotation.RInject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author shinelon
 */
public class DetailJob implements Runnable, Serializable {

    @RInject
    private RedissonClient redisson;
    @Autowired
    private BizService bizService;

    private TaskBean taskBean;

    public DetailJob(TaskBean taskBean) {
        this.taskBean = taskBean;
    }

    @Override
    public void run() {
        RPermitExpirableSemaphore semaphore = redisson.getPermitExpirableSemaphore(taskBean.getTaskId());
        semaphore.addPermits(taskBean.getWorkerCount());
        List<TaskDetailBean> details = taskBean.getDetails();
        for (TaskDetailBean detail : details) {
            bizService.bizExecute(taskBean.getTaskId(), detail);
        }
    }
}
