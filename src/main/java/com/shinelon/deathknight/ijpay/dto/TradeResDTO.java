package com.shinelon.deathknight.ijpay.dto;

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
}
