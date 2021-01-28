package com.shinelon.deathknight.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/***
 * @author shinelon
 */
@Service
public class MethodTryService {
    private static final Logger logger = LoggerFactory.getLogger(MethodTryService.class);

    public String methodNone() {
        logger.info("methodNone");
        throw new RuntimeException("ok1111");
    }

    public String methodTwo(String one, String two) {
        logger.info("methodTwo");
        return "retOk2";
    }

    public void methodThree(String one, String two, String three) {
        logger.info("methodThree");
    }
}
