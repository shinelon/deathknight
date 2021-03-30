package com.shinelon.deathknight.ijpay.service.remote.log.alipay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayObject;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.*;
import com.alipay.api.response.*;
import com.shinelon.deathknight.ijpay.service.remote.log.TradeLog;

/**
 * @author Shinelon
 * @date 2021-03-26 17:46
 */
public class AlipayTradeLog extends TradeLog {

    public <R extends AlipayResponse, T extends AlipayObject>
    void saveLog(T t, R r) {
        String requestJson = JSON.toJSONString(t);
        JSONObject requestObj = JSON.parseObject(requestJson);
        String tradeNo = requestObj.getString("trade_no");
        String resopnseJson = JSON.toJSONString(r);
    }

    public <T extends AlipayObject> void saveLog(T t, String r) {
        String requestJson = JSON.toJSONString(t);
        JSONObject requestObj = JSON.parseObject(requestJson);
        String tradeNo = requestObj.getString("trade_no");
    }

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

    protected void saveLog(AlipayTradeQueryModel model, AlipayTradeQueryResponse alipayTradeQueryResponse) {
        //        super.updateLog();
        //支付中不保存
        if ("WAIT_BUYER_PAY".equals(alipayTradeQueryResponse.getTradeStatus())) {
            return;
        }
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
