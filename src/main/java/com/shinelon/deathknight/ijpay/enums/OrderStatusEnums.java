package com.shinelon.deathknight.ijpay.enums;

import com.shinelon.deathknight.ijpay.exception.PayException;
import lombok.Getter;

/**
 * @author Shinelon
 */
@Getter
public enum OrderStatusEnums {
    /**
     * 初始
     */
    INIT("init", "初始"),
    /***
     * 支付中
     */
    PAYING("paying", "支付中"),
    /***
     * 退款中
     */
    REFUNDING("refunding", "退款中"),
    /***
     *支付成功
     */
    PAY_SUCCESS("pay_success", "支付成功"),
    /***
     *退款成功
     */
    REFUND_SUCCESS("refund_success", "退款成功"),
    /***
     *支付失败
     */
    PAY_FAILED("pay_failed", "支付失败"),
    /***
     *退款失败
     */
    REFUND_FAILED("refund_failed", "退款失败");

    private final String code;
    private final String msg;

    OrderStatusEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static boolean isSuccess(String code) {
        OrderStatusEnums enumByCode = getEnumByCode(code);
        return PAY_SUCCESS.equals(enumByCode) || REFUND_SUCCESS.equals(enumByCode);
    }

    public static boolean isFailed(String code) {
        OrderStatusEnums enumByCode = getEnumByCode(code);
        return PAY_FAILED.equals(enumByCode) || REFUND_FAILED.equals(enumByCode);
    }

    public static boolean isInProcess(String code) {
        OrderStatusEnums enumByCode = getEnumByCode(code);
        return PAYING.equals(enumByCode) || REFUNDING.equals(enumByCode);
    }

    public static OrderStatusEnums getEnumByCode(String code) {
        OrderStatusEnums[] values = OrderStatusEnums.values();
        for (OrderStatusEnums tmp : values) {
            if (tmp.code.equals(code)) {
                return tmp;
            }
        }
        throw new PayException("error orderStatus code!");
    }
}
