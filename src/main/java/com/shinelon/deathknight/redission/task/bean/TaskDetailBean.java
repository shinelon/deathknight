package com.shinelon.deathknight.redission.task.bean;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shinelon
 */
@Data
@Builder
public class TaskDetailBean implements Serializable {
    private String detailId;
    private String content;

}
