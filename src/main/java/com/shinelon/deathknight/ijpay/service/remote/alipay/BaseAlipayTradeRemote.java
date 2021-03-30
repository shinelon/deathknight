package com.shinelon.deathknight.ijpay.service.remote.alipay;

import com.alipay.api.AlipayObject;
import com.alipay.api.AlipayResponse;
import com.shinelon.deathknight.ijpay.exception.PayException;
import com.shinelon.deathknight.ijpay.service.remote.log.alipay.AlipayTradeLog;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Shinelon
 * @date 2021-03-29 10:59
 */
@Slf4j
public abstract class BaseAlipayTradeRemote extends AlipayTradeLog {
    /**
     * 执行远程调用
     *
     * @param t
     * @param func
     * @param <R>
     * @param <T>
     * @return
     */
    public <R extends AlipayResponse, T extends AlipayObject>
    R call(T t, AlipayCallFunction<T, R> func) {
        try {
            R res = func.apply(t);
            saveLog(t, res);
            return res;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            saveLog(t, e.getMessage());
            throw new PayException(e.getMessage());
        }
    }


}
