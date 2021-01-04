package com.shinelon.deathknight.redission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class TaskJob implements Runnable, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(TaskJob.class);

    private String name;

    public TaskJob() {
    }

    public TaskJob(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        if (Thread.currentThread().isInterrupted()) {
            // task has been canceled
            logger.info("job:{} has been canceled", name);
            return;
        }
        logger.info("job:{} is done", name);
    }
}
