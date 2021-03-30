package com.shinelon.deathknight.ijpay.service.remote.wechat.impl;

import cn.hutool.json.JSONUtil;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.utils.DateTimeZoneUtil;
import com.ijpay.wxpay.enums.WxApiType;
import com.ijpay.wxpay.model.CloseOrderModel;
import com.ijpay.wxpay.model.OrderQueryModel;
import com.ijpay.wxpay.model.v3.Amount;
import com.ijpay.wxpay.model.v3.UnifiedOrderModel;
import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatPayReq;
import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatPayRes;
import com.shinelon.deathknight.ijpay.service.remote.wechat.BaseWechatTradeRemote;
import com.shinelon.deathknight.ijpay.service.remote.wechat.IWechatPayRemote;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Shinelon
 * @date 2021-03-25 15:07
 */
@Service
@Slf4j
public class WechatPayRemoteImpl extends BaseWechatTradeRemote implements IWechatPayRemote {

    @Override
    public WechatPayRes payNative(WechatPayReq wechatPayReq) {
        UnifiedOrderModel orderModel = convertUnifiedOrderModel(wechatPayReq);
        IJPayHttpResponse response = super.call(WxApiType.NATIVE_PAY.toString(), JSONUtil.toJsonStr(orderModel), basePostCall);
        WechatPayRes wechatPayRes = convertPayNativeWechatPayRes(response);
        wechatPayRes.setOrderNo(wechatPayReq.getOrderNo());
        return wechatPayRes;
    }

    @Override
    public WechatPayRes query(WechatPayReq wechatPayReq) {
        OrderQueryModel queryModel = convertOrderQueryModel(wechatPayReq);
        IJPayHttpResponse response = super.call(WxApiType.ORDER_QUERY.toString(), JSONUtil.toJsonStr(queryModel), basePostCall);
        return convertOrderQueryWechatPayRes(response);
    }

    @Override
    public WechatPayRes close(WechatPayReq wechatPayReq) {
        CloseOrderModel closeOrderModel = convertCloseOrderModel(wechatPayReq);
        IJPayHttpResponse response = super.call(WxApiType.CLOSE_ORDER.toString(), JSONUtil.toJsonStr(closeOrderModel), basePostCall);
        return convertBaseWechatPayRes(response);
    }


    private CloseOrderModel convertCloseOrderModel(WechatPayReq wechatPayReq) {
        OrderBean orderBean = wechatPayReq.getOrderBean();
        return CloseOrderModel.builder().out_trade_no(orderBean.getOrderNo()).appid(wxPayV3Bean.getAppId())
                .mch_id(wxPayV3Bean.getMchId()).build();
    }


    private WechatPayRes convertOrderQueryWechatPayRes(IJPayHttpResponse response) {
        WechatPayRes wechatPayRes = convertBaseWechatPayRes(response);
        if (wechatPayRes.getIsSuccess()) {
            String body = response.getBody();
            wechatPayRes.setOrderNo(getStringFromJson(body, IWechatPayRemote.OUT_TRADE_NO));
            wechatPayRes.setTradeNo(getStringFromJson(body, IWechatPayRemote.TRANSACTION_ID));
            wechatPayRes.setTradeStatus(getStringFromJson(body, IWechatPayRemote.TRADE_STATE));
        }
        return wechatPayRes;
    }

    private OrderQueryModel convertOrderQueryModel(WechatPayReq wechatPayReq) {
        OrderBean orderBean = wechatPayReq.getOrderBean();
        OrderQueryModel model = OrderQueryModel.builder()
                .appid(wxPayV3Bean.getAppId())
                .mch_id(wxPayV3Bean.getMchId())
                .transaction_id(orderBean.getTradeNo())
                .build();
        wechatPayReq.setRequestBody(toBodyJson(model));
        return model;
    }


    private WechatPayRes convertPayNativeWechatPayRes(IJPayHttpResponse response) {
        WechatPayRes wechatPayRes = convertBaseWechatPayRes(response);
        if (wechatPayRes.getIsSuccess()) {
            wechatPayRes.setCodeUrl(getStringFromJson(response.getBody(), CODE_URL));
        }
        return wechatPayRes;
    }


    private WechatPayRes failedWechatPayRes() {
        WechatPayRes res = new WechatPayRes();
        res.setIsSuccess(false);
        res.setResMsg("WechatPayException");
        return res;
    }

    private UnifiedOrderModel convertUnifiedOrderModel(WechatPayReq wechatPayReq) {
        OrderBean orderBean = wechatPayReq.getOrderBean();
        BigDecimal totalAmount = orderBean.getTotalAmount();
        BigDecimal amount = totalAmount.multiply(BigDecimal.valueOf(100L));
        UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                .setAppid(wxPayV3Bean.getAppId())
                .setMchid(wxPayV3Bean.getMchId())
                .setDescription(orderBean.getGoodsDescription())
                .setOut_trade_no(orderBean.getOrderNo())
                .setTime_expire(timeExpire())
                .setNotify_url(wxPayV3Bean.getDomain().concat(NOTIFY_URL))
                .setAmount(new Amount().setTotal(amount.intValue()));
        if (!StringUtils.isBlank(orderBean.getPassbackParams())) {
            unifiedOrderModel.setAttach(orderBean.getPassbackParams());
        }
        wechatPayReq.setRequestBody(toBodyJson(unifiedOrderModel));
        return unifiedOrderModel;
    }

    private String timeExpire() {
        try {
            return DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 15);
        } catch (Exception e) {
            throw new RuntimeException("timeExpire error");
        }
    }

}
