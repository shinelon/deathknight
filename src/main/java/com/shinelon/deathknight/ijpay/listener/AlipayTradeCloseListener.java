package com.shinelon.deathknight.ijpay.listener;

import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.enums.PayChannelEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Shinelon
 */
@Slf4j
@Component("alipay-trade-close-listener")
public class AlipayTradeCloseListener implements ITradeCloseListener<OrderBean> {

    @Override
    public void invoke(OrderBean orderBean) {
        if (!PayChannelEnum.ALIPAY.getCode().equals(orderBean.getPayChannel())) {
            return;
        }
        log.info("AlipayTradeCloseListener");
    }
}
