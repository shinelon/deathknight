package com.shinelon.deathknight.ijpay.service.remote.alipay.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.ijpay.alipay.AliPayApi;
import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.config.AliPayBean;
import com.shinelon.deathknight.ijpay.dto.alipay.AlipayPayReq;
import com.shinelon.deathknight.ijpay.dto.alipay.AlipayPayRes;
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
public class AlipayPayRemoteImpl implements IAlipayPayRemote {

    @Autowired
    private AliPayBean aliPayBean;

    @Override
    public void pcPay(AlipayPayReq alipayPayReq, HttpServletResponse response) {
        try {
            String returnUrl = aliPayBean.getDomain() + RETURN_URL;
            String notifyUrl = aliPayBean.getDomain() + NOTIFY_URL;
            AlipayTradePagePayModel model = convertAlipayTradePagePayModel(alipayPayReq);
            AliPayApi.tradePage(response, model, notifyUrl, returnUrl);
        } catch (Exception e) {
            log.error("pcPay.errorMsg:{}", e.getMessage());
            log.error(e.getMessage(), e);
        }

    }

    @Override
    public AlipayPayRes close(AlipayPayReq alipayPayReq) {
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        try {
            AlipayTradeCloseResponse alipayTradeCloseResponse = AliPayApi.tradeCloseToResponse(model);
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
            AlipayTradeQueryResponse alipayTradeQueryResponse = AliPayApi.tradeQueryToResponse(model);
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

    private AlipayTradeQueryModel covertAlipayTradeQueryModel(AlipayPayReq alipayPayReq) {
        OrderBean orderBean = alipayPayReq.getOrderBean();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setTradeNo(orderBean.getTradeNo());
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
        model.setEnablePayChannels("balance,bankPay,debitCardExpress");
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
