package com.shinelon.deathknight.ijpay.config;

import com.alipay.api.AlipayApiException;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * @author Shinelon
 */
@Configuration
public class AliPayCfg {
    @Autowired
    private AliPayBean aliPayBean;

    @Bean
    public AliPayApiConfig aliPayApiConfig() throws AlipayApiException {
        AliPayApiConfig aliPayApiConfig = AliPayApiConfig.builder()
                .setAppId(aliPayBean.getAppId())
                .setAliPayPublicKey(aliPayBean.getPublicKey())
                .setCharset("UTF-8")
                .setPrivateKey(aliPayBean.getPrivateKey())
                .setServiceUrl(aliPayBean.getServerUrl())
                .setSignType("RSA2")
                // 普通公钥方式
                .build();
        AliPayApiConfigKit.putApiConfig(aliPayApiConfig);
        return aliPayApiConfig;
    }
}
