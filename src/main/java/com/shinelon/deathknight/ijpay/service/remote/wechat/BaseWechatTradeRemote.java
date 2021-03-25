package com.shinelon.deathknight.ijpay.service.remote.wechat;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.WxPayKit;
import com.shinelon.deathknight.ijpay.config.WxPayV3Bean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.cert.X509Certificate;

/**
 * @author Shinelon
 * @date 2021-03-25 15:30
 */
@Slf4j
public abstract class BaseWechatTradeRemote {
    /***
     * 获取证书序列号
     */
    protected String serialNo;
    /**
     * 获取平台证书序列号
     */
    protected String platSerialNo;

    @Autowired
    protected WxPayV3Bean wxPayV3Bean;

    protected String getSerialNumber() {
        if (StrUtil.isEmpty(serialNo)) {
            // 获取证书序列号
            X509Certificate certificate = PayKit.getCertificate(FileUtil.getInputStream(wxPayV3Bean.getCertPath()));
            serialNo = certificate.getSerialNumber().toString(16).toUpperCase();

        }
        return serialNo;
    }

    protected String getPlatSerialNumber() {
        if (StrUtil.isEmpty(platSerialNo)) {
            // 获取平台证书序列号
            X509Certificate certificate = PayKit.getCertificate(FileUtil.getInputStream(wxPayV3Bean.getPlatformCertPath()));
            platSerialNo = certificate.getSerialNumber().toString(16).toUpperCase();
        }
        return platSerialNo;
    }


    protected boolean verifySignature(IJPayHttpResponse response) {
        try {
            return WxPayKit.verifySignature(response, wxPayV3Bean.getPlatformCertPath());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
