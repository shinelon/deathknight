package com.shinelon.deathknight.ijpay.service.impl;

import com.shinelon.deathknight.ijpay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/***
 * @author Shinelon
 */
@Service("alipay-pay-service")
@Slf4j
public class AlipayPayServiceImpl implements IPayService {
    @Override
    public void query() {

    }

    @Override
    public void pay() {
        log.info("alipay-pay-service.pay");
    }

    @Override
    public void close() {

    }

    @Override
    public void payNotify() {

    }
}
