package com.shinelon.deathknight.ijpay.service.remote.log.alipay;

import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.shinelon.deathknight.ijpay.service.remote.log.TradeLog;

/**
 * @author Shinelon
 * @date 2021-03-26 17:56
 */
public class AlipayRefundLog extends TradeLog {

    protected Long saveLog(AlipayTradeRefundModel refundModel) {
        //        super.saveReqLog()
        return Long.valueOf(0);
    }

    protected void updateLog(Long id, AlipayTradeRefundResponse alipayTradeRefundResponse) {
        //        super.updateLog();
    }


    protected Long saveLog(AlipayTradeFastpayRefundQueryModel refundQueryModel) {
        //        super.saveReqLog()
        return Long.valueOf(0);
    }

    protected void updateLog(Long id, AlipayTradeFastpayRefundQueryResponse refundQueryResponse) {
        //        super.updateLog();
    }
}
