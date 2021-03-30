package com.shinelon.deathknight.ijpay.service.remote.wechat;

/**
 * @author Shinelon
 * @date 2021-03-30 17:33
 */
@FunctionalInterface
public interface WechatCallFunction<T, U, R> {
    /**
     * apply
     *
     * @param t
     * @param u
     * @return
     * @throws Exception
     */
    R apply(T t, U u) throws Exception;
}
