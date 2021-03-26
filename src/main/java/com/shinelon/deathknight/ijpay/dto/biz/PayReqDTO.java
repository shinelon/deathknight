package com.shinelon.deathknight.ijpay.dto.biz;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * @author Shinelon
 * @date 2021-03-26 15:42
 */
@Data
public class PayReqDTO {

    private String userId;
    private String token;
    private String payChannel;
    private String payType;
    private BigDecimal totalAmount;
    private String goodsName;
    private String goodsDescription;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
