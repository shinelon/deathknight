package com.shinelon.deathknight.ijpay.dto.remote.alipay;

import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.dto.remote.TradeReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Shinelon
 * @date 2021-03-24 16:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlipayPayReq extends TradeReqDTO {
    private OrderBean orderBean;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
