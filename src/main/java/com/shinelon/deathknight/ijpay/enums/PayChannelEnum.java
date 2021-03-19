package com.shinelon.deathknight.ijpay.enums;

import com.shinelon.deathknight.ijpay.exception.PayException;
import lombok.Getter;

/**
 * @author Shinelon
 */
@Getter
public enum PayChannelEnum {
    /***
     * 支付宝支付
     */
    ALIPAY("alipay",
            "支付宝支付",
            "alipay-pay-service",
            "alipay-trade-close-queue",
            "alipay-trade-close-listener"),
    /***
     * 微信支付
     */
    WECHAT("wechat",
            "微信支付",
            "wechat-pay-service",
            "wechat-trade-close-queue",
            "wechat-trade-close-listener");

    private final String code;
    private final String msg;
    private final String payServiceName;
    private final String tradeCloseQueueName;
    private final String tradeCloseListenerName;

    PayChannelEnum(String code, String msg,
                   String payServiceName,
                   String tradeCloseQueueName,
                   String tradeCloseListenerName) {
        this.code = code;
        this.msg = msg;
        this.payServiceName = payServiceName;
        this.tradeCloseQueueName = tradeCloseQueueName;
        this.tradeCloseListenerName = tradeCloseListenerName;
    }

    /***
     * getEnumByCode
     * @param code
     * @return
     */
    public static PayChannelEnum getEnumByCode(String code) {
        PayChannelEnum[] values = PayChannelEnum.values();
        for (PayChannelEnum tmp : values) {
            if (tmp.code.equals(code)) {
                return tmp;
            }
        }
        throw new PayException(PayCodeEnum.ILLEGAL_PAY_CHANNEL_CODE);
    }

    /***
     * getBeanName
     * @param code
     * @return
     */
    public static String getPayServiceName(String code) {
        return getEnumByCode(code).getPayServiceName();
    }

    /***
     * tradeCloseQueueName
     * @param code
     * @return
     */
    public static String tradeCloseQueueName(String code) {
        return getEnumByCode(code).getTradeCloseQueueName();
    }

    /***
     * tradeCloseListenerName
     * @param code
     * @return
     */
    public static String tradeCloseListenerName(String code) {
        return getEnumByCode(code).getTradeCloseListenerName();
    }

}
