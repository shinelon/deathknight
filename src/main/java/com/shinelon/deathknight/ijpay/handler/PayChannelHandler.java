package com.shinelon.deathknight.ijpay.handler;

import com.shinelon.deathknight.ijpay.enums.PayChannelEnum;
import com.shinelon.deathknight.ijpay.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/***
 * @author Shinelon
 */
@Component
public class PayChannelHandler {
    @Autowired
    private Map<String, IPayService> payServiceMap;

    /***
     * payService
     * @param payChannelCode
     * @return
     */
    public IPayService payService(String payChannelCode) {
        return payServiceMap.get(PayChannelEnum.getBeanName(payChannelCode));
    }
}
