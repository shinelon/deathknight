package com.shinelon.deathknight.ijpay.dto.wechat;

import com.shinelon.deathknight.ijpay.dto.TradeReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * @author Shinelon
 * @date 2021-03-25 14:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WechatRefundReq extends TradeReqDTO {
    private String refundNo;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
