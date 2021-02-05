package com.shinelon.deathknight.config.mdc;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/***
 * @author Shinelon
 */
public class MdcTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                if (!CollectionUtils.isEmpty(contextMap)) {
                    MDC.setContextMap(contextMap);
                }
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
