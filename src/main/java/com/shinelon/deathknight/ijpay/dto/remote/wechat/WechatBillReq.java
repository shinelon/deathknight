package com.shinelon.deathknight.ijpay.dto.remote.wechat;

import com.shinelon.deathknight.ijpay.dto.remote.TradeReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Shinelon
 * @date 2021-03-25 15:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WechatBillReq extends TradeReqDTO {
    private String billType;
    private String billDate;
    private String tarType;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}