package com.shinelon.deathknight.ijpay.service.remote.wechat;

import com.shinelon.deathknight.ijpay.dto.wechat.WechatCertRes;

/**
 * @author Shinelon
 * @date 2021-03-25 17:10
 */
public interface IWechatCertificateRemoteService extends IWechatTradeRemote {

    /**
     * 平台首次上线需要创建证书
     *
     * @return
     */
    WechatCertRes platformCert();

    /***
     * 更新平台证书，需要定时更新
     * @return
     */
    WechatCertRes platformCertUpdate();
}
