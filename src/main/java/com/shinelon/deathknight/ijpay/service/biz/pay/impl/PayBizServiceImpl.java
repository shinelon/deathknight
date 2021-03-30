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
        checkToken(payReqDTO.getUserId(), payReqDTO.getToken());
        AlipayPayReq alipayPayReq = new AlipayPayReq();
        OrderBean orderBean = OrderBean.builder()
                .orderNo(nextSeqNo())
                .goodsName(payReqDTO.getGoodsName())
                .totalAmount(payReqDTO.getTotalAmount())
                .orderStatus(OrderStatusEnums.PAYING.toString())
                .build();
        alipayPayReq.setOrderBean(orderBean);
        insertOrder(orderBean);
        alipayPayRemote.pcPay(alipayPayReq, response);

    }


    @Override
    public PayResDTO alipayQrCode(PayReqDTO payReqDTO) {
        checkToken(payReqDTO.getUserId(), payReqDTO.getToken());
        AlipayPayReq alipayPayReq = new AlipayPayReq();
        OrderBean orderBean = OrderBean.builder()
                .orderNo(nextSeqNo())
                .goodsName(payReqDTO.getGoodsName())
                .totalAmount(payReqDTO.getTotalAmount())
                .orderStatus(OrderStatusEnums.PAYING.toString())
                .build();
        alipayPayReq.setOrderBean(orderBean);
        //getBody
        insertOrder(orderBean);
        AlipayPayRes alipayPayRes = alipayPayRemote.qrPay(alipayPayReq);
        String generateAsBase64 = getQrCodeBase64(alipayPayRes.getCodeUrl());
        return PayResDTO.builder().qrCodeImg(generateAsBase64).build();
    }

    @Override
    public PayResDTO wechatNative(PayReqDTO payReqDTO) {
        checkToken(payReqDTO.getUserId(), payReqDTO.getToken());
        WechatPayReq wechatPayReq = new WechatPayReq();
        OrderBean orderBean = OrderBean.builder()
                .orderNo(nextSeqNo())
                .goodsDescription(payReqDTO.getGoodsName())
                .totalAmount(payReqDTO.getTotalAmount())
                .build();
        wechatPayReq.setOrderBean(orderBean);
        insertOrder(orderBean);
        WechatPayRes wechatPayRes = wechatPayRemote.payNative(wechatPayReq);
        updateOrder(orderBean);
        String generateAsBase64 = getQrCodeBase64(wechatPayRes.getCodeUrl());
        return PayResDTO.builder().qrCodeImg(generateAsBase64).build();
    }

    private String getQrCodeBase64(String codeUrl) {
        QrConfig qrConfig = QrConfig.create();
        return QrCodeUtil.generateAsBase64(codeUrl, qrConfig, ImgUtil.IMAGE_TYPE_JPEG);
    }


    @Override
    public PayResDTO query(String payChannel, String orderNo) {
        PayResDTO res = PayResDTO.builder().build();
        if (PayChannelEnum.ALIPAY.getCode().equals(payChannel)) {
            AlipayPayReq alipayPayReq = new AlipayPayReq();
            alipayPayReq.setOrderNo(orderNo);
            AlipayPayRes queryRes = alipayPayRemote.query(alipayPayReq);
            //转换
            return res;
        }
        if (PayChannelEnum.WECHAT.getCode().equals(payChannel)) {
            WechatPayReq wechatPayReq = new WechatPayReq();
            wechatPayReq.setOrderNo(orderNo);
            WechatPayRes queryRes = wechatPayRemote.query(wechatPayReq);
            //转换
            return res;
        }
        throw new PayException(PayCodeEnum.ILLEGAL_PAY_CHANNEL_CODE);
    }

    @Override
    public void close(String payChannel, String orderNo) {
        PayResDTO res = PayResDTO.builder().build();
        if (PayChannelEnum.ALIPAY.getCode().equals(payChannel)) {
            AlipayPayReq alipayPayReq = new AlipayPayReq();
            alipayPayReq.setOrderNo(orderNo);
            AlipayPayRes queryRes = alipayPayRemote.close(alipayPayReq);
            //转换
        }
        if (PayChannelEnum.WECHAT.getCode().equals(payChannel)) {
            WechatPayReq wechatPayReq = new WechatPayReq();
            wechatPayReq.setOrderNo(orderNo);
            WechatPayRes queryRes = wechatPayRemote.close(wechatPayReq);
            //转换
        }
        throw new PayException(PayCodeEnum.ILLEGAL_PAY_CHANNEL_CODE);
    }


    private void insertOrder(OrderBean orderBean) {
        //首次请求后，加入到延迟队列
        addQueue(orderBean);
    }

    private void updateOrder(OrderBean orderBean) {

    }

}
