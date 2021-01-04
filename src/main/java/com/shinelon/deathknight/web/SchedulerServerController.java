package com.shinelon.deathknight.web;

import com.shinelon.deathknight.redission.DelayedQueueService;
import com.shinelon.deathknight.redission.SchedulerServer;
import com.shinelon.deathknight.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
public class SchedulerServerController {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerServerController.class);
    @Autowired
    private SchedulerServer schedulerServer;
    @Autowired
    private DelayedQueueService delayedQueueService;

    @RequestMapping("/delayQuery")
    public String delayQuery() {
        delayedQueueService.delayQueue();
        return "ok";
    }

    @RequestMapping("/list")
    public String listJob() {
        Set<String> strings = schedulerServer.listJob();
        return JsonUtil.toJsonString(strings);
    }

    @RequestMapping("/cannel")
    public String cannel(String taskId) {
        schedulerServer.cannelTask(taskId);
        return "ok--" + taskId;
    }

    @RequestMapping("/addJob")
    public String addJob() {
        schedulerServer.addJob();
        logger.info("add...");
        return "ok";
    }


    @RequestMapping("/delete")
    public String deleteScheduled() {
        schedulerServer.deleteScheduled();
        logger.info("delete...");
        return "ok";
    }
}
