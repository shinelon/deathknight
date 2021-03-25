package com.shinelon.deathknight.ijpay.service.biz.impl;

import com.shinelon.deathknight.ijpay.handler.PayChannelHandler;
import com.shinelon.deathknight.ijpay.service.biz.IPayBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Shinelon
 */
@Service
public class PayBizServiceImpl implements IPayBizService {
    @Autowired
    private PayChannelHandler payChannelHandler;

    public void pay(String payChannel) {

    }


}
