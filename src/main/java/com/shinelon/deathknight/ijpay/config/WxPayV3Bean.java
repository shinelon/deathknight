package com.shinelon.deathknight.ijpay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/***
 * @author Shinelon
 */
@Component
@PropertySource("classpath:/wxpay_v3.properties")
@ConfigurationProperties(prefix = "v3")
@Data
public class WxPayV3Bean {
    private String appId;
    private String keyPath;
    private String certPath;
    private String certP12Path;
    private String platformCertPath;
    private String mchId;
    private String apiKey;
    private String apiKey3;
    private String domain;
}
