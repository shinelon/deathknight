package com.shinelon.deathknight.redission.task.service;

import com.shinelon.deathknight.redission.task.bean.TaskDetailBean;

/**
 * @author shinelon
 */
public interface BizService {
    /***
     * bizExecute
     * @param taskId
     * @param taskDetailBean
     */
    void bizExecute(String taskId, TaskDetailBean taskDetailBean);
}
