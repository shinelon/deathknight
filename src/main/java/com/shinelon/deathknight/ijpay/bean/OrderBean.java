package com.shinelon.deathknight.ijpay.bean;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Shinelon
 */
@Data
@Builder
public class OrderBean implements Serializable {
    private String payChannel;
    private LocalDateTime createTime;
    /***
     * 订单号
     */
    private String orderNo;
    /**
     * 支付渠道交易号
     */
    private String tradeNo;
    /***
     * 订单金额
     */
    private BigDecimal totalAmount;
    /***
     * 订单状态
     */
    private String orderStatus;
    /***
     * 支付渠道交易状态
     */
    private String tradeStatus;
    /**
     * 退款号
     */
    private String refundNo;
    /***
     * 商品名
     */
    private String goodsName;
    /**
     * 商品描述
     */
    private String goodsDescription;
    /**
     * 自定义参数
     */
    private String passbackParams;
}
