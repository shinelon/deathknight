package com.shinelon.deathknight.ijpay.dto.alipay;

import com.shinelon.deathknight.ijpay.dto.TradeReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * @author Shinelon
 * @date 2021-03-25 10:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlipayRefundReq extends TradeReqDTO {
    private String orderNo;
    private String tradeNo;
    private String refundNo;
    private BigDecimal refundAmount;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
