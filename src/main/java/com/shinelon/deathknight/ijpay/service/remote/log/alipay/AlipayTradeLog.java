package com.shinelon.deathknight.ijpay.service.remote.log.alipay;

import com.alipay.api.domain.*;
import com.alipay.api.response.*;
import com.shinelon.deathknight.ijpay.service.remote.log.TradeLog;

/**
 * @author Shinelon
 * @date 2021-03-26 17:46
 */
public class AlipayTradeLog extends TradeLog {

    protected void saveLog(AlipayTradePagePayModel model) {
        //        super.saveReqLog()
    }

    protected Long saveLog(AlipayTradePrecreateModel model) {
        //        super.saveReqLog()
        return Long.valueOf(0);
    }

    protected void updateLog(Long id, AlipayTradePrecreateResponse alipayTradeQueryResponse) {
        //        super.updateLog();
    }

    protected Long saveLog(AlipayTradeQueryModel model) {
        //        super.saveReqLog()
        return Long.valueOf(0);
    }

    protected void updateLog(Long id, AlipayTradeQueryResponse alipayTradeQueryResponse) {
        //        super.updateLog();
    }


    protected Long saveLog(AlipayTradeCloseModel model) {
        //        super.saveReqLog()
        return Long.valueOf(0);
    }

    protected void updateLog(Long id, AlipayTradeCloseResponse alipayTradeCloseResponse) {
        //        super.updateLog();
    }


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
