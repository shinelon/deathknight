package com.shinelon.deathknight.ijpay.handler;

import com.shinelon.deathknight.ijpay.enums.PayChannelEnum;
import com.shinelon.deathknight.ijpay.listener.ITradeCloseListener;
import com.shinelon.deathknight.ijpay.service.remote.IPayService;
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
    @Autowired
    private Map<String, ITradeCloseListener> tradeCloseListenerMap;

    /***
     * payService
     * @param payChannelCode
     * @return
     */
    public IPayService payService(String payChannelCode) {
        return payServiceMap.get(PayChannelEnum.getPayServiceName(payChannelCode));
    }

    /***
     * tradeCloseListener
     * @param payChannelCode
     * @return
     */
    public ITradeCloseListener tradeCloseListener(String payChannelCode) {
        return tradeCloseListenerMap.get(PayChannelEnum.tradeCloseListenerName(payChannelCode));
    }
}
