package com.shinelon.deathknight.ijpay.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/***
 * 业务账单原始明细（部分）
 * @author Shinelon
 */
@Data
public class BizBillDetailBean {
    /***
     * 订单号
     */
    private String orderNo;
    /**
     * 支付渠道交易号
     */
    private String tradeNo;
    /**
     * 退款号
     */
    private String refundNo;
    /***
     * 业务类型
     */
    private String bizType;
    /***
     * 创建时间
     */
    private LocalDateTime createTime;
    /***
     * 完成时间
     */
    private LocalDateTime finishTime;
    /***
     * 订单金额
     */
    private BigDecimal totalAmount;
    /***
     * 商家实收
     */
    private BigDecimal realIncome;

}
