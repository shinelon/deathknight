package com.shinelon.deathknight.ijpay.service.remote.wechat;

import com.ijpay.core.IJPayHttpResponse;
import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatPayReq;
import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatPayRes;

/**
 * @author Shinelon
 * @date 2021-03-25 10:38
 */
public interface IWechatPayRemote extends IWechatTradeRemote {
    /**
     * 支付通知地址
     */
    String NOTIFY_URL = "/v3/notify_url";
    /***
     * payNative.code_url
     */
    String CODE_URL = "code_url";
    /**
     * 订单号
     */
    String OUT_TRADE_NO = "out_trade_no";
    /**
     * 交易号
     */
    String TRANSACTION_ID = "transaction_id";
    /**
     * 交易状态
     */
    String TRADE_STATE = "trade_state";

    /***
     * payNative
     * @param wechatPayReq
     * @return
     */
    WechatPayRes payNative(WechatPayReq wechatPayReq);

    /**
     * query
     *
     * @param wechatPayReq
     * @return
     */
    WechatPayRes query(WechatPayReq wechatPayReq);

    /**
     * close
     *
     * @param wechatPayReq
     * @return
     */
    WechatPayRes close(WechatPayReq wechatPayReq);


    /***
     * convertBaseWechatPayRes
     * @param response
     * @return
     */
    default WechatPayRes convertBaseWechatPayRes(IJPayHttpResponse response) {
        WechatPayRes res = new WechatPayRes();
        boolean isSuccess = isSuccessCode(response.getStatus());
        String body = response.getBody();
        res.setIsSuccess(isSuccess);
        res.setResponseBody(body);
        return res;
    }
}
