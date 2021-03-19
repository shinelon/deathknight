package com.shinelon.deathknight.ijpay.runners;

import com.alibaba.fastjson.JSON;
import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.enums.PayChannelEnum;
import com.shinelon.deathknight.ijpay.handler.PayChannelHandler;
import com.shinelon.deathknight.ijpay.handler.TradeCloseDelayedQueue;
import com.shinelon.deathknight.ijpay.listener.ITradeCloseListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * @author Shinelon
 */
@Component
@Slf4j
public class DelayedQueueRunner implements ApplicationRunner {
    @Autowired
    @Qualifier("delayedQueuePool")
    private ExecutorService delayedQueuePool;
    @Autowired
    private TradeCloseDelayedQueue tradeCloseDelayedQueue;
    @Autowired
    private PayChannelHandler payChannelHandler;
    @Autowired
    @Qualifier("tradeCloseBizPool")
    private ExecutorService tradeCloseBizPool;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        PayChannelEnum[] values = PayChannelEnum.values();
        for (PayChannelEnum payChannelEnum : values) {
            tradeCloseDelayedQueue.initQueue(payChannelEnum.getCode());
            delayedQueuePool.execute(() -> {
                log.info("{}.take is startup!", payChannelEnum.getTradeCloseQueueName());
                while (true) {
                    OrderBean orderBean = tradeCloseDelayedQueue.takeQueue(payChannelEnum.getCode());
                    log.info("take.value:{}", JSON.toJSONString(orderBean));
                    tradeCloseBizPool.execute(() -> {
                        ITradeCloseListener tradeCloseListener = payChannelHandler.tradeCloseListener(orderBean.getPayChannel());
                        tradeCloseListener.invoke(orderBean);
                    });
                }
            });

        }
    }
}
