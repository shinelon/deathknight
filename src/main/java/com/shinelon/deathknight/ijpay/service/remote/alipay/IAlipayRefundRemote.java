package com.shinelon.deathknight.ijpay.service.remote.alipay;

import com.shinelon.deathknight.ijpay.dto.remote.alipay.AlipayRefundReq;
import com.shinelon.deathknight.ijpay.dto.remote.alipay.AlipayRefundRes;

/**
 * @author Shinelon
 * @date 2021-03-25 10:36
 */
public interface IAlipayRefundRemote extends IAlipayTradeService {

    /**
     * 支付宝退款
     *
     * @param alipayRefundReq
     * @return
     */
    AlipayRefundRes refund(AlipayRefundReq alipayRefundReq);

    /**
     * 支付宝退款查询
     *
     * @param alipayRefundReq
     * @return
     */
    AlipayRefundRes query(AlipayRefundReq alipayRefundReq);
}
