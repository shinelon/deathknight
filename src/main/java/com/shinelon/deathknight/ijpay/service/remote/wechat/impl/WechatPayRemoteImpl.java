package com.shinelon.deathknight.ijpay.service.remote.wechat.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethod;
import com.ijpay.core.utils.DateTimeZoneUtil;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxApiType;
import com.ijpay.wxpay.enums.WxDomain;
import com.ijpay.wxpay.model.v3.Amount;
import com.ijpay.wxpay.model.v3.UnifiedOrderModel;
import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.dto.wechat.WechatPayReq;
import com.shinelon.deathknight.ijpay.dto.wechat.WechatPayRes;
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

    public WechatPayRes payNative(WechatPayReq wechatPayReq) {
        UnifiedOrderModel orderModel = convertUnifiedOrderModel(wechatPayReq);
        try {
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethod.POST,
                    WxDomain.CHINA.toString(),
                    WxApiType.NATIVE_PAY.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    JSONUtil.toJsonStr(orderModel)
            );

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public String getString(String params, String field) {
        JSONObject jsonObject = JSON.parseObject(params);
        return jsonObject.getString(field);
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
