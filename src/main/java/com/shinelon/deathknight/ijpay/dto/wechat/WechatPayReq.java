package com.shinelon.deathknight.ijpay.dto.wechat;

import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.dto.TradeReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shinelon
 * @date 2021-03-24 17:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WechatPayReq extends TradeReqDTO {
    private OrderBean orderBean;
}
