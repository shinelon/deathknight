package com.shinelon.deathknight.ijpay.dto.remote.wechat;

import com.shinelon.deathknight.ijpay.dto.remote.TradeResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Shinelon
 * @date 2021-03-24 17:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WechatPayRes extends TradeResDTO {
    /***
     * 订单号
     */
    private String orderNo;
    /**
     * 二维码url
     */
    private String codeUrl;
    /**
     * 完成时间
     */
    private String finishTime;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
