package com.shinelon.deathknight.ijpay.dto.biz;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Shinelon
 * @date 2021-03-26 15:42
 */
public class RefundReqDTO {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
