package com.shinelon.deathknight.ijpay.dto.alipay;

import com.shinelon.deathknight.ijpay.dto.TradeResDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Shinelon
 * @date 2021-03-25 10:43
 */
public class AlipayRefundRes extends TradeResDTO {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
