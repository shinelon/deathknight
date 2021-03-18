package com.shinelon.deathknight.ijpay.exception;

import com.shinelon.deathknight.ijpay.enums.PayCodeEnum;
import lombok.Getter;

/***
 * @author Shinelon
 */
@Getter
public class PayException extends RuntimeException {

    private PayCodeEnum payCodeEnum;

    public PayException(PayCodeEnum payCodeEnum) {
        super(payCodeEnum.getMsg());
        this.payCodeEnum = payCodeEnum;
    }

    public PayException(String message) {
        super(message);
    }
}
