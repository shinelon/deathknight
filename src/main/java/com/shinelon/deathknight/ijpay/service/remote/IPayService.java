package com.shinelon.deathknight.ijpay.service.remote;

/***
 * @author Shinelon
 */
public interface IPayService {
    /***
     *query
     */
    void query();

    /***
     *pay
     */
    void pay();

    /***
     *close
     */
    void close();

    /***
     * 支付异步通知，可能延迟，建议使用查询为准
     */
    void payNotify();
}
