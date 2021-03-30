package com.shinelon.deathknight.ijpay.service.remote.alipay.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.ijpay.alipay.AliPayApi;
import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.config.AliPayBean;
import com.shinelon.deathknight.ijpay.dto.remote.alipay.AlipayPayReq;
import com.shinelon.deathknight.ijpay.dto.remote.alipay.AlipayPayRes;
import com.shinelon.deathknight.ijpay.service.remote.alipay.BaseAlipayTradeRemote;
import com.shinelon.deathknight.ijpay.service.remote.alipay.IAlipayPayRemote;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Shinelon
 * @date 2021-03-25 10:08
 */
@Slf4j
@Service
public class AlipayPayRemoteImpl extends BaseAlipayTradeRemote implements IAlipayPayRemote {

    @Autowired
    private AliPayBean aliPayBean;

    @Override
    public void pcPay(AlipayPayReq alipayPayReq, HttpServletResponse response) {
        try {
            String returnUrl = aliPayBean.getDomain() + RETURN_URL;
            String notifyUrl = aliPayBean.getDomain() + NOTIFY_URL;
            AlipayTradePagePayModel model = convertAlipayTradePagePayModel(alipayPayReq);
            super.saveLog(model);
            AliPayApi.tradePage(response, model, notifyUrl, returnUrl);
        } catch (Exception e) {
            log.error("pcPay.errorMsg:{}", e.getMessage());
            log.error(e.getMessage(), e);
        }

    }

    @Override
    public AlipayPayRes qrPay(AlipayPayReq alipayPayReq) {
        try {
            String notifyUrl = aliPayBean.getDomain() + NOTIFY_URL;
            AlipayTradePrecreateModel model = convertAlipayTradePrecreateModel(alipayPayReq);
            Long logId = super.saveLog(model);
            AlipayTradePrecreateResponse alipayTradePrecreateResponse = AliPayApi.tradePrecreatePayToResponse(model, notifyUrl);
            super.updateLog(logId, alipayTradePrecreateResponse);
            return convertAlipayPayRes(alipayTradePrecreateResponse);
        } catch (AlipayApiException e) {
            log.error("qrPay.errorMsg:{}", e.getMessage());
            log.error(e.getMessage(), e);
        }
        return failedAlipayPayRes();
    }

    @Override
    public AlipayPayRes close(AlipayPayReq alipayPayReq) {
        AlipayTradeCloseModel model = convertAlipayTradeCloseModel(alipayPayReq);
        try {
            Long logId = super.saveLog(model);
            AlipayTradeCloseResponse alipayTradeCloseResponse = AliPayApi.tradeCloseToResponse(model);
            super.updateLog(logId, alipayTradeCloseResponse);
            return convertAlipayPayRes(alipayTradeCloseResponse);
        } catch (AlipayApiException e) {
            log.error("close.errorMsg:{},errorCode:{}", e.getErrMsg(), e.getErrCode());
            log.error(e.getMessage(), e);
        }
        return failedAlipayPayRes();
    }

    @Override
    public AlipayPayRes query(AlipayPayReq alipayPayReq) {
        AlipayTradeQueryModel model = covertAlipayTradeQueryModel(alipayPayReq);
        try {
            Long logId = super.saveLog(model);
            AlipayTradeQueryResponse alipayTradeQueryResponse = AliPayApi.tradeQueryToResponse(model);
            super.updateLog(logId, alipayTradeQueryResponse);
            return convertAlipayPayRes(alipayTradeQueryResponse);
        } catch (AlipayApiException e) {
            log.error("query.errorMsg:{},errorCode:{}", e.getErrMsg(), e.getErrCode());
            log.error(e.getMessage(), e);
        }
        return failedAlipayPayRes();
    }

    private AlipayPayRes failedAlipayPayRes() {
        AlipayPayRes res = new AlipayPayRes();
        res.setIsSuccess(false);
        res.setResMsg("AlipayApiException");
        return res;
    }

    private AlipayPayRes convertAlipayPayRes(AlipayTradePrecreateResponse response) {
        AlipayPayRes res = new AlipayPayRes();
        res.setIsSuccess(isSuccessCode(response.getCode()));
        res.setResponseBody(response.getBody());
        res.setCodeUrl(response.getQrCode());
        res.setOrderNo(response.getOutTradeNo());
        return res;
    }

    private AlipayPayRes convertAlipayPayRes(AlipayTradeCloseResponse response) {
        AlipayPayRes res = new AlipayPayRes();
        res.setIsSuccess(isSuccessCode(response.getCode()));
        res.setResponseBody(response.getBody());
        return res;
    }

    private AlipayPayRes convertAlipayPayRes(AlipayTradeQueryResponse response) {
        AlipayPayRes res = new AlipayPayRes();
        res.setTradeStatus(response.getTradeStatus());
        res.setIsSuccess(isSuccessCode(response.getCode()));
        res.setResponseBody(response.getBody());
        return res;
    }

    private AlipayTradeCloseModel convertAlipayTradeCloseModel(AlipayPayReq alipayPayReq) {
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setTradeNo(alipayPayReq.getTradeNo());
        alipayPayReq.setRequestBody(toBodyJson(model));
        return model;
    }

    private AlipayTradeQueryModel covertAlipayTradeQueryModel(AlipayPayReq alipayPayReq) {
        OrderBean orderBean = alipayPayReq.getOrderBean();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setTradeNo(orderBean.getTradeNo());
        alipayPayReq.setRequestBody(toBodyJson(model));
        return model;
    }

    private AlipayTradePrecreateModel convertAlipayTradePrecreateModel(AlipayPayReq alipayPayReq) {
        OrderBean orderBean = alipayPayReq.getOrderBean();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(orderBean.getOrderNo());
        model.setTotalAmount(orderBean.getTotalAmount().toPlainString());
        model.setSubject(orderBean.getGoodsName());
        model.setBody(orderBean.getGoodsDescription());
        if (!StringUtils.isBlank(orderBean.getPassbackParams())) {
            //本参数必须进行UrlEncode
            String encode = urlEncode(orderBean.getPassbackParams());
            model.setPassbackParams(encode);
        }
        model.setTimeoutExpress("30m");
        alipayPayReq.setRequestBody(toBodyJson(model));
        return model;
    }

    private AlipayTradePagePayModel convertAlipayTradePagePayModel(AlipayPayReq alipayPayReq) {
        OrderBean orderBean = alipayPayReq.getOrderBean();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(orderBean.getOrderNo());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        model.setTotalAmount(orderBean.getTotalAmount().toPlainString());
        model.setSubject(orderBean.getGoodsName());
        model.setBody(orderBean.getGoodsDescription());
        if (!StringUtils.isBlank(orderBean.getPassbackParams())) {
            //本参数必须进行UrlEncode
            String encode = urlEncode(orderBean.getPassbackParams());
            model.setPassbackParams(encode);
        }
        model.setGoodsType("0");
        model.setTimeoutExpress("30m");
        alipayPayReq.setRequestBody(toBodyJson(model));
        return model;
    }

    private String urlEncode(String encode) {
        try {
            return URLEncoder.encode(encode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return encode;
    }
}
