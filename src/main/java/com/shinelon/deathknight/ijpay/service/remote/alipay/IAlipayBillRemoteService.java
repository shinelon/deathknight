package com.shinelon.deathknight.ijpay.service.remote.alipay;

import com.shinelon.deathknight.ijpay.dto.alipay.AlipayBillReq;
import com.shinelon.deathknight.ijpay.dto.alipay.AlipayBillRes;

/**
 * @author Shinelon
 * @date 2021-03-25 10:37
 */
public interface IAlipayBillRemoteService extends IAlipayTradeService {

    /**
     * downloadUrl
     *
     * @param alipayBillReq
     * @return
     */
    AlipayBillRes downloadUrl(AlipayBillReq alipayBillReq);
}
