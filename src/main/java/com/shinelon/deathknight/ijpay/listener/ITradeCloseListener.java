package com.shinelon.deathknight.ijpay.listener;

/***
 * @author Shinelon
 */
@FunctionalInterface
public interface ITradeCloseListener<T> {
    /***
     * invoke
     * @param t
     */
    void invoke(T t);
}
