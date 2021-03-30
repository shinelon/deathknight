package com.shinelon.deathknight.ijpay.service.remote.alipay;

/**
 * @author Shinelon
 * @date 2021-03-30 16:39
 */
@FunctionalInterface
public interface AlipayCallFunction<T, R> {
    /***
     *  执行请求
     * @param t
     * @return R
     * @throws Exception
     */
    R apply(T t) throws Exception;
}
