package com.shinelon.deathknight.redission.task.bean;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author shinelon
 */
@Data
@Builder
public class TaskBean implements Serializable {
    private String taskId;
    private String taskContent;
    private Integer workerCount;
    private String scheduleJobId;
    private String scheduleJobStatus;
    private List<TaskDetailBean> details;

}
