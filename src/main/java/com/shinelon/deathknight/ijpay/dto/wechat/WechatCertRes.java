package com.shinelon.deathknight.ijpay.dto.wechat;

import com.shinelon.deathknight.ijpay.dto.TradeResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Shinelon
 * @date 2021-03-25 17:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WechatCertRes extends TradeResDTO {
    private String serialNumber;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
