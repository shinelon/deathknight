package com.shinelon.deathknight.ijpay.dto.remote;

import lombok.Data;

/**
 * @author Shinelon
 * @date 2021-03-24 16:33
 */
@Data
public class TradeResDTO {
    /**
     * 请求是否成功
     */
    protected Boolean isSuccess;
    /**
     * 返回信息
     */
    protected String resMsg;
    /**
     * 返回体
     */
    protected String responseBody;
    /**
     * 订单号
     */
    protected String orderNo;
    /**
     * 交易订单
     */
    protected String tradeNo;
    /**
     * 交易状态
     */
    protected String tradeStatus;
}