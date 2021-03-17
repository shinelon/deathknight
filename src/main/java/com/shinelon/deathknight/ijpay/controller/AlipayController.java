package com.shinelon.deathknight.ijpay.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import com.shinelon.deathknight.ijpay.config.AliPayBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * @author Shinelon
 */
@Controller
@RequestMapping("/alipay")
@Slf4j
public class AlipayController {
    private final static String NOTIFY_URL = "/aliPay/notify_url";
    private final static String RETURN_URL = "/aliPay/return_url";
    @Autowired
    private AliPayBean aliPayBean;

    /**
     * PC支付 ijpay
     */
    @RequestMapping(value = "/pcPay/ijpay")
    @ResponseBody
    public void pcPay(HttpServletResponse response) {
        try {
            AliPayApiConfig aliPayApiConfig = AliPayApiConfigKit.getAliPayApiConfig();
            log.info("aliPayApiConfig:{}", JSON.toJSONString(aliPayApiConfig));
            String totalAmount = "88.88";
            String outTradeNo = IdUtil.simpleUUID();
            log.info("pc outTradeNo :" + outTradeNo);

            String returnUrl = aliPayBean.getDomain() + RETURN_URL;
            String notifyUrl = aliPayBean.getDomain() + NOTIFY_URL;
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setOutTradeNo(outTradeNo);
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            model.setTotalAmount(totalAmount);
            model.setSubject("Iphone6 16G");
            model.setBody(" Iphone6 16G");
            //本参数必须进行UrlEncode
            model.setPassbackParams("merchantBizType%3d3C%26merchantBizNo%3d2016010101111");
            model.setGoodsType("0");
            model.setTimeoutExpress("30m");
            model.setEnablePayChannels("balance,bankPay,debitCardExpress");
            AliPayApi.tradePage(response, model, notifyUrl, returnUrl);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * PC支付Sdk
     */
    @RequestMapping(value = "/pcPay/sdk")
    @ResponseBody
    public void pcPaySdk(HttpServletResponse response) throws AlipayApiException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                aliPayBean.getServerUrl(),
                aliPayBean.getAppId(),
                aliPayBean.getPrivateKey(),
                "json", "UTF-8",
                aliPayBean.getPublicKey(),
                "RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        String outTradeNo = IdUtil.simpleUUID();
        String totalAmount = "88.88";
        log.info("pc outTradeNo :" + outTradeNo);
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(outTradeNo);
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        model.setTotalAmount(totalAmount);
        model.setSubject("Iphone6 16G");
        model.setBody(" Iphone6 16G");
        //本参数必须进行UrlEncode
        model.setPassbackParams("merchantBizType%3d3C%26merchantBizNo%3d2021030101111");
        model.setGoodsType("0");
        model.setTimeoutExpress("30m");
        model.setEnablePayChannels("balance,bankPay,debitCardExpress");
        request.setBizModel(model);
        AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(request);
        log.info("response:{}", JSON.toJSONString(alipayTradePagePayResponse));
        String form = alipayTradePagePayResponse.getBody();
        response.setContentType("text/html;charset=" + AliPayApiConfigKit.getAliPayApiConfig().getCharset());
        PrintWriter out = response.getWriter();
        out.write(form);
        out.flush();
        out.close();
    }
}
