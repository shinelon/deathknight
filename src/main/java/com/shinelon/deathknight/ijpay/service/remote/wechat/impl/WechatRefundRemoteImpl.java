package com.shinelon.deathknight.ijpay.service.remote.wechat.impl;

import cn.hutool.json.JSONUtil;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethod;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxApiType;
import com.ijpay.wxpay.enums.WxDomain;
import com.ijpay.wxpay.model.v3.RefundAmount;
import com.ijpay.wxpay.model.v3.RefundModel;
import com.shinelon.deathknight.ijpay.dto.wechat.WechatRefundReq;
import com.shinelon.deathknight.ijpay.dto.wechat.WechatRefundRes;
import com.shinelon.deathknight.ijpay.service.remote.wechat.BaseWechatTradeRemote;
import com.shinelon.deathknight.ijpay.service.remote.wechat.IWechatRefundRemote;
import com.shinelon.deathknight.ijpay.service.remote.wechat.IWechatTradeRemote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Shinelon
 * @date 2021-03-26 11:39
 */
@Service
@Slf4j
public class WechatRefundRemoteImpl extends BaseWechatTradeRemote implements IWechatRefundRemote {

    @Override
    public WechatRefundRes refund(WechatRefundReq wechatRefundReq) {
        RefundModel refundModel = convertRefundModel(wechatRefundReq);
        try {
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethod.POST,
                    WxDomain.CHINA.toString(),
                    WxApiType.DOMESTIC_REFUNDS.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    JSONUtil.toJsonStr(refundModel)
            );
            assertVerifySignatureTrue(response);
            return convertWechatRefundRes(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return failedWechatRefundRes();
    }


    @Override
    public WechatRefundRes query(WechatRefundReq wechatRefundReq) {
        RefundModel refundQueryModel = convertRefundQueryModel(wechatRefundReq);
        try {
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethod.POST,
                    WxDomain.CHINA.toString(),
                    WxApiType.DOMESTIC_REFUNDS_QUERY.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    JSONUtil.toJsonStr(refundQueryModel)
            );
            assertVerifySignatureTrue(response);
            return convertWechatRefundRes(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return failedWechatRefundRes();
    }

    private WechatRefundRes convertWechatRefundRes(IJPayHttpResponse response) {
        WechatRefundRes res = convertBaseWechatRefundRes(response);
        String body = response.getBody();
        res.setRefundChannelNo(getStringFromJson(body, "refund_id"));
        res.setRefundNo(getStringFromJson(body, "out_refund_no"));
        res.setTradeNo(getStringFromJson(body, "transaction_id"));
        res.setOrderNo(getStringFromJson(body, "out_trade_no"));
        res.setTradeStatus(getStringFromJson(body, "status"));
        return res;
    }

    private RefundModel convertRefundQueryModel(WechatRefundReq wechatRefundReq) {
        RefundModel model = new RefundModel()
                .setOut_refund_no(wechatRefundReq.getRefundNo());
        return model;
    }

    private RefundModel convertRefundModel(WechatRefundReq wechatRefundReq) {
        int totalAmount = wechatRefundReq.getTotalAmount().multiply(BigDecimal.valueOf(100L)).intValue();
        int refundAmount = wechatRefundReq.getRefundAmount().multiply(BigDecimal.valueOf(100L)).intValue();
        RefundAmount refundAmountBean = new RefundAmount()
                .setTotal(totalAmount)
                .setRefund(refundAmount)
                .setCurrency(IWechatTradeRemote.CNY);
        RefundModel model = new RefundModel()
                .setOut_refund_no(wechatRefundReq.getRefundNo())
                .setTransaction_id(wechatRefundReq.getTradeNo())
                .setAmount(refundAmountBean);
        return model;
    }

    private WechatRefundRes failedWechatRefundRes() {
        WechatRefundRes res = new WechatRefundRes();
        res.setIsSuccess(false);
        res.setResMsg("WechatRefundException");
        return res;
    }
}
