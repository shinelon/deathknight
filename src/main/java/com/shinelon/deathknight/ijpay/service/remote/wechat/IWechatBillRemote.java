package com.shinelon.deathknight.ijpay.service.remote.wechat;

import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatBillReq;
import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatBillRes;

/**
 * @author Shinelon
 * @date 2021-03-25 10:39
 */
public interface IWechatBillRemote extends IWechatTradeRemote {

    /**
     * downloadUrl
     *
     * @param wechatBillReq
     * @return
     */
    WechatBillRes downloadUrl(WechatBillReq wechatBillReq);
}
