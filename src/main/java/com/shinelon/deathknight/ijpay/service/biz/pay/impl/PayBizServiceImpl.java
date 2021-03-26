package com.shinelon.deathknight.ijpay.service.biz.pay.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.dto.biz.PayReqDTO;
import com.shinelon.deathknight.ijpay.dto.biz.PayResDTO;
import com.shinelon.deathknight.ijpay.dto.remote.alipay.AlipayPayReq;
import com.shinelon.deathknight.ijpay.dto.remote.alipay.AlipayPayRes;
import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatPayReq;
import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatPayRes;
import com.shinelon.deathknight.ijpay.enums.OrderStatusEnums;
import com.shinelon.deathknight.ijpay.enums.PayChannelEnum;
import com.shinelon.deathknight.ijpay.enums.PayCodeEnum;
import com.shinelon.deathknight.ijpay.exception.PayException;
import com.shinelon.deathknight.ijpay.service.biz.pay.BasePayService;
import com.shinelon.deathknight.ijpay.service.biz.pay.IPayBizService;
import com.shinelon.deathknight.ijpay.service.remote.alipay.IAlipayPayRemote;
import com.shinelon.deathknight.ijpay.service.remote.wechat.IWechatPayRemote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Shinelon
 */
@Service
@Slf4j
public class PayBizServiceImpl extends BasePayService implements IPayBizService {
    @Autowired
    private IAlipayPayRemote alipayPayRemote;
    @Autowired
    private IWechatPayRemote wechatPayRemote;

    @Override
    public PayResDTO payToken(String userId) {
        String token = generateToken(userId);
        return PayResDTO.builder().token(token).build();
    }

    @Override
    public void alipayPc(PayReqDTO payReqDTO, HttpServletResponse response) {
        AlipayPayReq alipayPayReq = new AlipayPayReq();
        OrderBean orderBean = OrderBean.builder()
                .orderNo(nextSeqNo())
                .goodsName(payReqDTO.getGoodsName())
                .totalAmount(payReqDTO.getTotalAmount())
                .orderStatus(OrderStatusEnums.PAYING.toString())
                .build();
        alipayPayReq.setOrderBean(orderBean);
        //getBody
        insertOrder();
        alipayPayRemote.pcPay(alipayPayReq, response);

    }

    @Override
    public PayResDTO wechatNative(PayReqDTO payReqDTO) {
        WechatPayReq wechatPayReq = new WechatPayReq();
        OrderBean orderBean = OrderBean.builder()
                .orderNo(nextSeqNo())
                .goodsDescription(payReqDTO.getGoodsName())
                .totalAmount(payReqDTO.getTotalAmount())
                .build();
        wechatPayReq.setOrderBean(orderBean);
        insertOrder();
        WechatPayRes wechatPayRes = wechatPayRemote.payNative(wechatPayReq);
        updateOrder();
        String codeUrl = wechatPayRes.getCodeUrl();
        QrConfig qrConfig = QrConfig.create();
        String generateAsBase64 = QrCodeUtil.generateAsBase64(codeUrl, qrConfig, ImgUtil.IMAGE_TYPE_JPEG);
        return PayResDTO.builder().qrCodeImg(generateAsBase64).build();
    }


    @Override
    public PayResDTO query(String payChannel, String tradeNo) {
        PayResDTO res = PayResDTO.builder().build();
        if (PayChannelEnum.ALIPAY.getCode().equals(payChannel)) {
            AlipayPayReq alipayPayReq = new AlipayPayReq();
            alipayPayReq.setTradeNo(tradeNo);
            AlipayPayRes queryRes = alipayPayRemote.query(alipayPayReq);
            //转换
            return res;
        }
        if (PayChannelEnum.WECHAT.getCode().equals(payChannel)) {
            WechatPayReq wechatPayReq = new WechatPayReq();
            wechatPayReq.setTradeNo(tradeNo);
            WechatPayRes queryRes = wechatPayRemote.query(wechatPayReq);
            //转换
            return res;
        }
        throw new PayException(PayCodeEnum.ILLEGAL_PAY_CHANNEL_CODE);
    }

    @Override
    public void close(String payChannel, String tradeNo) {
        PayResDTO res = PayResDTO.builder().build();
        if (PayChannelEnum.ALIPAY.getCode().equals(payChannel)) {
            AlipayPayReq alipayPayReq = new AlipayPayReq();
            alipayPayReq.setTradeNo(tradeNo);
            AlipayPayRes queryRes = alipayPayRemote.close(alipayPayReq);
            //转换
        }
        if (PayChannelEnum.WECHAT.getCode().equals(payChannel)) {
            WechatPayReq wechatPayReq = new WechatPayReq();
            wechatPayReq.setTradeNo(tradeNo);
            WechatPayRes queryRes = wechatPayRemote.close(wechatPayReq);
            //转换
        }
        throw new PayException(PayCodeEnum.ILLEGAL_PAY_CHANNEL_CODE);
    }


    private void insertOrder() {
        //首次请求后，加入到延迟队列
    }

    private void updateOrder() {

    }

}
