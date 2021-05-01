package com.shinelon.deathknight.redission.task.service;

import com.shinelon.deathknight.redission.task.bean.TaskBean;

import java.util.List;

/**
 * @author shinelon
 */
public interface TaskManageService {
    /**
     * getTaskList
     *
     * @return
     */
    List<TaskBean> getTaskList();

    /**
     * startScheduler
     *
     * @param todoList
     */
    void startScheduler(List<TaskBean> todoList);
}
