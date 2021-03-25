package com.shinelon.deathknight.ijpay.service.remote.wechat;

/**
 * @author Shinelon
 * @date 2021-03-25 10:38
 */
public interface IWechatPayRemote extends IWechatTradeRemote {
    /**
     * 支付通知地址
     */
    String NOTIFY_URL = "/v3/notify_url";
}
