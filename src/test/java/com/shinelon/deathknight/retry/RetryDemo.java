package com.shinelon.deathknight.retry;

import com.shinelon.deathknight.DeathknightApplicationTests;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.time.Duration;

/***
 * @author shinelon
 */

public class RetryDemo extends DeathknightApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(RetryDemo.class);
    @Autowired
    private MethodTryService methodTryService;
    @Autowired
    private MethodTryService2 methodTryService2;


    private RetryConfig config = RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofMillis(1000))
            .retryExceptions(Exception.class)
            .build();


    @Test
    public void retry1() {
        RetryRegistry registry = RetryRegistry.of(config);
        Retry retry = registry.retry("name1");
        CheckedFunction0<String> retryableSupplier =
                Retry.decorateCheckedSupplier(retry, () -> methodTryService.methodNone());
        registry.getEventPublisher();
        Try<String> result = Try.of(retryableSupplier)
                .andThenTry(e -> logger.info("andThen" + e))
                .onFailure(e -> logger.info("onFailure"))
                .recover((throwableSchedulerServer) -> "methodTryService recovery function");

        logger.info(result.toString());
    }

    @Test
    public void retry2() {
        methodTryService2.methodNone();
    }

    @Test
    public void retry3() throws Throwable {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy());
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(3000);
        backOffPolicy.setMultiplier(2);
        backOffPolicy.setMaxInterval(15000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        retryTemplate.execute(new RetryCallback<String, Throwable>() {
            @Override
            public String doWithRetry(RetryContext context) throws Throwable {
                methodTryService2.methodTwo("1", "2");
                return "String";
            }
        });
    }
}