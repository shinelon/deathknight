package com.shinelon.deathknight.ijpay.enums;

import lombok.Getter;

/***
 * @author Shinelon
 */
@Getter
public enum PayCodeEnum {
    /***
     * 成功
     */
    SUCCESS("200", "success"),
    /***
     * 失败
     */
    FAILED("400", "failed"),
    /***
     * 非法
     */
    ILLEGAL("401", "illegal"),

    /***
     * token非法
     */
    ILLEGAL_PAY_TOKEN("402", "illegal_pay_token"),
    /***
     * 非法支付渠道码
     */
    ILLEGAL_PAY_CHANNEL_CODE("403", "illegal_pay_channel_code");

    private final String code;
    private final String msg;

    PayCodeEnum(String codeStr, String msg) {
        this.code = codeStr;
        this.msg = msg;
    }
}
