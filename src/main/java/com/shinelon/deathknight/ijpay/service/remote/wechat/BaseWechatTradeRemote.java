package com.shinelon.deathknight.ijpay.service.remote.wechat;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethod;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxDomain;
import com.shinelon.deathknight.ijpay.config.WxPayV3Bean;
import com.shinelon.deathknight.ijpay.enums.PayCodeEnum;
import com.shinelon.deathknight.ijpay.exception.PayException;
import com.shinelon.deathknight.ijpay.service.remote.log.wechat.WechatTradeLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.cert.X509Certificate;

/**
 * @author Shinelon
 * @date 2021-03-25 15:30
 */
@Slf4j
public abstract class BaseWechatTradeRemote extends WechatTradeLog {
    /***
     * 获取证书序列号
     */
    private volatile static String serialNo;
    /**
     * 获取平台证书序列号
     */
    private volatile static String platSerialNo;


    @Autowired
    protected WxPayV3Bean wxPayV3Bean;

    protected static void resetSerialNo() {
        serialNo = null;
    }

    protected static void resetPlatSerialNo() {
        platSerialNo = null;
    }


    protected WechatCallFunction<String, String, IJPayHttpResponse> basePostCall = (String url, String json) -> {
        return WxPayApi.v3(
                RequestMethod.POST,
                WxDomain.CHINA.toString(),
                url,
                wxPayV3Bean.getMchId(),
                getSerialNumber(),
                null,
                wxPayV3Bean.getKeyPath(),
                json
        );
    };

    protected IJPayHttpResponse call(String url, String jsonStr
            , WechatCallFunction<String, String, IJPayHttpResponse> function) {
        try {
            IJPayHttpResponse res = function.apply(url, jsonStr);
            assertVerifySignatureTrue(res);
            //savelog
            return res;
        } catch (Exception e) {
            throw new PayException(e.getMessage());
        }
    }

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


    protected void assertVerifySignatureTrue(IJPayHttpResponse response) {
        if (!verifySignature(response)) {
            log.error("errorSignatureResponse:{}", JSONUtil.toJsonPrettyStr(response));
            throw new PayException(PayCodeEnum.ILLEGAL_SIGNATURE);
        }
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
