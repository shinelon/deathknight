package com.shinelon.deathknight.ijpay.service.remote.wechat;

import com.ijpay.core.IJPayHttpResponse;
import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatRefundReq;
import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatRefundRes;

/**
 * @author Shinelon
 * @date 2021-03-25 10:39
 */
public interface IWechatRefundRemote extends IWechatTradeRemote {
    /**
     * refund
     *
     * @param wechatRefundReq
     * @return
     */
    WechatRefundRes refund(WechatRefundReq wechatRefundReq);

    /***
     * query
     * @param wechatRefundReq
     * @return
     */
    WechatRefundRes query(WechatRefundReq wechatRefundReq);

    /***
     * convertBaseWechatPayRes
     * @param response
     * @return
     */
    default WechatRefundRes convertBaseWechatRefundRes(IJPayHttpResponse response) {
        WechatRefundRes res = new WechatRefundRes();
        boolean isSuccess = isSuccessCode(response.getStatus());
        String body = response.getBody();
        res.setIsSuccess(isSuccess);
        res.setResponseBody(body);
        return res;
    }
}
