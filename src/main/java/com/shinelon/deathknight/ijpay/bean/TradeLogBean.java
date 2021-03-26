package com.shinelon.deathknight.ijpay.bean;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Shinelon
 * @date 2021-03-26 17:38
 */
@Data
public class TradeLogBean {

    private Long logId;
    /***
     * 订单号
     */
    private String orderNo;
    /**
     * 支付渠道交易号
     */
    private String tradeNo;
    /**
     * 退款号
     */
    private String refundNo;

    private String payChannel;
    /**
     * requestName
     */
    private String requestName;

    private String requestContent;
    private String responseContent;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
