package com.shinelon.deathknight.ijpay.dto.alipay;

import com.shinelon.deathknight.ijpay.dto.TradeReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Shinelon
 * @date 2021-03-25 10:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlipayBillReq extends TradeReqDTO {
    private String billType;
    private String billDate;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
