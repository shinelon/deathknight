package com.shinelon.deathknight.ijpay.service.impl;

import com.shinelon.deathknight.ijpay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/***
 * @author Shinelon
 */
@Service("wechat-pay-service")
@Slf4j
public class WechatPayServiceImpl implements IPayService {
    @Override
    public void query() {

    }

    @Override
    public void pay() {
        log.info("wechat-pay-service.pay");
    }

    @Override
    public void close() {

    }

    @Override
    public void payNotify() {

    }
}
