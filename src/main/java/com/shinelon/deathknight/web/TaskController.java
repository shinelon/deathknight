package com.shinelon.deathknight.web;

import com.alibaba.fastjson.JSON;
import com.shinelon.deathknight.redission.task.bean.TaskBean;
import com.shinelon.deathknight.redission.task.service.TaskManageService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shinelon
 */
@RestController
@Slf4j
@RequestMapping("/task")
@ConditionalOnBean(value = RedissonClient.class)
public class TaskController {
    @Autowired
    private TaskManageService taskManageService;

    @RequestMapping("/start")
    public String start() {
        List<TaskBean> taskList = taskManageService.getTaskList();
        log.info("taskId:{}", JSON.toJSONString(taskList));
        taskManageService.startScheduler(taskList);
        return "ok";
    }
}
