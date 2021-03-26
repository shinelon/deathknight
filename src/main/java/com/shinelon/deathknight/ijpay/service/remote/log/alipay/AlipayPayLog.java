package com.shinelon.deathknight.ijpay.service.remote.log.alipay;

import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.shinelon.deathknight.ijpay.service.remote.log.TradeLog;

/**
 * @author Shinelon
 * @date 2021-03-26 17:46
 */
public class AlipayPayLog extends TradeLog {

    protected Long saveLog(AlipayTradePagePayModel model) {
        //        super.saveReqLog()
        return Long.valueOf(0);
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
}
