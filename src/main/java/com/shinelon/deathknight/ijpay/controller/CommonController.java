package com.shinelon.deathknight.ijpay.controller;

import com.alibaba.fastjson.JSON;
import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.handler.TradeCloseDelayedQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/***
 * @author Shinelon
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Autowired
    private TradeCloseDelayedQueue tradeCloseDelayedQueue;

    @RequestMapping("/testClose")
    public String addTestClose(String payChannel) {
        tradeCloseDelayedQueue.addQueue(OrderBean.builder()
                .createTime(LocalDateTime.now())
                .payChannel(payChannel).build());
        return "OK";
    }


    @RequestMapping("/take")
    public String take(String payChannel) {
        OrderBean orderBean = tradeCloseDelayedQueue.takeQueue(payChannel);
        log.info("take:{}", JSON.toJSONString(orderBean));
        return JSON.toJSONString(orderBean);
    }
}
