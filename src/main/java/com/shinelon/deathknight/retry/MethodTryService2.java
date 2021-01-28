package com.shinelon.deathknight.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/***
 * @author shinelon
 */
@Service
public class MethodTryService2 {
    private static final Logger logger = LoggerFactory.getLogger(MethodTryService2.class);

    @Retryable(value = RuntimeException.class, maxAttempts = 3, recover = "methodNoneRecover",
            backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public String methodNone() {
        logger.info("methodNone");
        throw new RuntimeException("ok1111");
    }

    @Recover
    public String methodNoneRecover(Throwable t) {
        logger.info("methodNoneRecover");
        logger.info(t.getMessage());
        return "ok2";
    }

    public String methodTwo(String one, String two) {
        logger.info("methodTwo");
        throw new RuntimeException("ok2222");
    }

    public void methodThree(String one, String two, String three) {
        logger.info("methodThree");
    }
}
