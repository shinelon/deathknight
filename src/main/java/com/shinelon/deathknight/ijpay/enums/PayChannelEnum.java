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
    ALIPAY("alipay", "支付宝支付", "alipay-pay-service"),
    /***
     * 微信支付
     */
    WECHAT("wechat", "微信支付", "wechat-pay-service");

    private final String code;
    private final String msg;
    private final String beanName;

    PayChannelEnum(String code, String msg, String beanName) {
        this.code = code;
        this.msg = msg;
        this.beanName = beanName;
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
    public static String getBeanName(String code) {
        return getEnumByCode(code).getBeanName();
    }

}
