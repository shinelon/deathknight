package com.shinelon.deathknight.ijpay.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import com.shinelon.deathknight.ijpay.config.AliPayBean;
import com.shinelon.deathknight.ijpay.config.WxPayV3Bean;
import com.shinelon.deathknight.ijpay.dto.biz.PayReqDTO;
import com.shinelon.deathknight.ijpay.dto.biz.PayResDTO;
import com.shinelon.deathknight.ijpay.enums.PayChannelEnum;
import com.shinelon.deathknight.ijpay.enums.PayCodeEnum;
import com.shinelon.deathknight.ijpay.exception.PayException;
import com.shinelon.deathknight.ijpay.service.biz.pay.IPayBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shinelon
 * @date 2021-03-29 16:51
 */
@RestController
@RequestMapping("/trade")
@Slf4j
public class TradePayController {
    @Autowired
    private IPayBizService payBizService;
    @Autowired
    private AliPayBean aliPayBean;
    @Autowired
    private WxPayV3Bean wxPayV3Bean;

    @RequestMapping("/pay")
    public String pay(PayReqDTO payReqDTO) {
        if (PayChannelEnum.ALIPAY.getCode().equals(payReqDTO.getPayChannel())) {
            PayResDTO resDTO = payBizService.alipayQrCode(payReqDTO);
            return JSON.toJSONString(resDTO);
        }
        if (PayChannelEnum.WECHAT.getCode().equals(payReqDTO.getPayChannel())) {
            PayResDTO resDTO = payBizService.wechatNative(payReqDTO);
            return JSON.toJSONString(resDTO);
        }
        throw new PayException(PayCodeEnum.ILLEGAL_PAY_CHANNEL_CODE);
    }

    public String query(String payChannel, String orderNo) {

        return null;
    }

    public String close(String payChannel, String orderNo) {
        return null;
    }

    @RequestMapping("/notify/alipay")
    public String notifyAlipay(HttpServletRequest request) {
        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = AliPayApi.toMap(request);
            log.debug("{}", JSON.toJSONString(params));
            boolean verifyResult = AlipaySignature.rsaCheckV1(params, aliPayBean.getPublicKey(), "UTF-8", "RSA2");
            //使用查询替代通知，只需要验证签名和风控，不需要进行业务处理
            if (verifyResult) {
                return "success";
            } else {
                return "failure";
            }
        } catch (AlipayApiException e) {
            log.error(e.getMessage(), e);
            return "failure";
        }
    }

    @RequestMapping(value = "/notify/wechat", method = {RequestMethod.POST, RequestMethod.GET})
    public void notifyWechat(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>(12);
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");

            log.info("timestamp:{} nonce:{} serialNo:{} signature:{}", timestamp, nonce, serialNo, signature);
            String result = HttpKit.readData(request);
            log.info("支付通知密文 {}", result);

            // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp,
                    wxPayV3Bean.getApiKey3(), wxPayV3Bean.getPlatformCertPath());

            log.info("支付通知明文 {}", plainText);

            if (StrUtil.isNotEmpty(plainText)) {
                response.setStatus(200);
                map.put("code", "SUCCESS");
                map.put("message", "SUCCESS");
            } else {
                response.setStatus(500);
                map.put("code", "ERROR");
                map.put("message", "签名错误");
            }
            response.setHeader("Content-type", ContentType.JSON.toString());
            response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
            response.flushBuffer();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}
