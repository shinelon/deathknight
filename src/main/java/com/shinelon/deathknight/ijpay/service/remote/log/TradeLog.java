package com.shinelon.deathknight.ijpay.service.remote.log;

import com.shinelon.deathknight.ijpay.bean.TradeLogBean;

/**
 * @author Shinelon
 * @date 2021-03-26 17:35
 */
public class TradeLog {


    /**
     * @param tradeLogBean
     * @return
     * @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
     */

    protected Long saveLog(TradeLogBean tradeLogBean) {
        return Long.valueOf(0);
    }

    /**
     * @param id
     * @param tradeLogBean
     * @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
     */
    protected void updateLog(Long id, TradeLogBean tradeLogBean) {
    }
}
