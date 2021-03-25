package com.shinelon.deathknight.ijpay.service.remote.alipay.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ijpay.alipay.AliPayApi;
import com.shinelon.deathknight.ijpay.dto.alipay.AlipayRefundReq;
import com.shinelon.deathknight.ijpay.dto.alipay.AlipayRefundRes;
import com.shinelon.deathknight.ijpay.service.remote.alipay.IAlipayRefundRemote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Shinelon
 * @date 2021-03-25 13:49
 */
@Service
@Slf4j
public class AlipayRefundRemoteImpl implements IAlipayRefundRemote {

    @Override
    public AlipayRefundRes refund(AlipayRefundReq alipayRefundReq) {
        AlipayTradeRefundModel refundModel = convertRefundModel(alipayRefundReq);
        try {
            AlipayTradeRefundResponse alipayTradeRefundResponse = AliPayApi.tradeRefundToResponse(refundModel);
            return convertAlipayRefundRes(alipayTradeRefundResponse);
        } catch (AlipayApiException e) {
            log.error("refund.errorMsg:{},errorCode:{}", e.getErrMsg(), e.getErrCode());
            log.error(e.getMessage(), e);
        }
        return failedAlipayRefundRes();
    }

    @Override
    public AlipayRefundRes query(AlipayRefundReq alipayRefundReq) {
        AlipayTradeFastpayRefundQueryModel refundQueryModel = convertRefundQueryModel(alipayRefundReq);
        try {
            AlipayTradeFastpayRefundQueryResponse refundQueryResponse = AliPayApi.tradeRefundQueryToResponse(refundQueryModel);
            return convertAlipayRefundRes(refundQueryResponse);
        } catch (AlipayApiException e) {
            log.error("refund.query.errorMsg:{},errorCode:{}", e.getErrMsg(), e.getErrCode());
            log.error(e.getMessage(), e);
        }
        return failedAlipayRefundRes();
    }

    private AlipayRefundRes convertAlipayRefundRes(AlipayTradeFastpayRefundQueryResponse refundQueryResponse) {
        AlipayRefundRes res = new AlipayRefundRes();
        res.setIsSuccess(isSuccessCode(refundQueryResponse.getCode()));
        res.setResponseBody(refundQueryResponse.getBody());
        return res;
    }

    private AlipayTradeFastpayRefundQueryModel convertRefundQueryModel(AlipayRefundReq alipayRefundReq) {
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setTradeNo(alipayRefundReq.getTradeNo());
        model.setOutRequestNo(alipayRefundReq.getRefundNo());
        alipayRefundReq.setRequestBody(toBodyJson(model));
        return model;
    }

    private AlipayRefundRes convertAlipayRefundRes(AlipayTradeRefundResponse alipayTradeRefundResponse) {
        AlipayRefundRes res = new AlipayRefundRes();
        res.setIsSuccess(isSuccessCode(alipayTradeRefundResponse.getCode()));
        res.setResponseBody(alipayTradeRefundResponse.getBody());
        return res;
    }

    private AlipayTradeRefundModel convertRefundModel(AlipayRefundReq alipayRefundReq) {
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setTradeNo(alipayRefundReq.getTradeNo());
        model.setOutRequestNo(alipayRefundReq.getRefundNo());
        model.setRefundAmount(alipayRefundReq.getRefundAmount().toPlainString());
        alipayRefundReq.setRequestBody(toBodyJson(model));
        return model;
    }

    private AlipayRefundRes failedAlipayRefundRes() {
        AlipayRefundRes res = new AlipayRefundRes();
        res.setIsSuccess(false);
        return res;
    }

}
