package com.shinelon.deathknight.ijpay.dto.remote.alipay;

import com.shinelon.deathknight.ijpay.dto.remote.TradeResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Shinelon
 * @date 2021-03-24 16:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlipayPayRes extends TradeResDTO {
    private String codeUrl;
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
