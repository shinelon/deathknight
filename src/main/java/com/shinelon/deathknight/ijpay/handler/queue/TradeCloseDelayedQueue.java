package com.shinelon.deathknight.ijpay.handler.queue;


import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.enums.PayChannelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Shinelon
 */
@Component
public class TradeCloseDelayedQueue {

    public static final long DEFAULT_CLOSE_SECONDS = 15 * 60L;
    @Autowired
    private DelayedQueueHandler delayedQueueHandler;

    public void addQueue(OrderBean orderBean) {
        addQueue(orderBean, DEFAULT_CLOSE_SECONDS, TimeUnit.SECONDS);
    }

    public void addQueue(OrderBean orderBean, long delay, TimeUnit timeUnit) {
        String payChannel = orderBean.getPayChannel();
        String tradeCloseQueueName = PayChannelEnum.tradeCloseQueueName(payChannel);
        delayedQueueHandler.addQueue(tradeCloseQueueName, orderBean, DEFAULT_CLOSE_SECONDS, TimeUnit.SECONDS);
    }

    public OrderBean takeQueue(String payChannel) {
        String tradeCloseQueueName = PayChannelEnum.tradeCloseQueueName(payChannel);
        return delayedQueueHandler.takeQueue(tradeCloseQueueName);
    }

    public void initQueue(String payChannel) {
        String tradeCloseQueueName = PayChannelEnum.tradeCloseQueueName(payChannel);
        delayedQueueHandler.initQueue(tradeCloseQueueName);
    }
}
