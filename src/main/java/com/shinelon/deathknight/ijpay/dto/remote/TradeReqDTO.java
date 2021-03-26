package com.shinelon.deathknight.ijpay.dto.remote;

import lombok.Data;

/**
 * @author Shinelon
 * @date 2021-03-24 16:33
 */
@Data
public class TradeReqDTO {
    protected String orderNo;
    protected String tradeNo;
    /**
     * 请求体
     */
    protected String requestBody;
}
