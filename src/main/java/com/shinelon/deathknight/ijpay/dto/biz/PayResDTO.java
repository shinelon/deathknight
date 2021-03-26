package com.shinelon.deathknight.ijpay.dto.biz;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Shinelon
 * @date 2021-03-26 15:51
 */
@Data
@Builder
public class PayResDTO {

    private String token;
    private String orderStatus;
    private String qrCodeImg;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
