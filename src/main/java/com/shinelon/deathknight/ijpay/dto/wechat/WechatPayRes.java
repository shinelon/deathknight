package com.shinelon.deathknight.ijpay.dto.wechat;

import com.shinelon.deathknight.ijpay.dto.TradeResDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shinelon
 * @date 2021-03-24 17:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WechatPayRes extends TradeResDTO {
    /**
     * 二维码url
     */
    private String codeUrl;
    /**
     * 完成时间
     */
    private String finishTime;
}
