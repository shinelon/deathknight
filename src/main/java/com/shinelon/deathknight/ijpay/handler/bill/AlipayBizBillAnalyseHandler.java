package com.shinelon.deathknight.ijpay.handler.bill;

import com.shinelon.deathknight.ijpay.bean.BizBillDetailBean;
import com.shinelon.deathknight.ijpay.constants.AlipayConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Shinelon
 */
@Component
public class AlipayBizBillAnalyseHandler {

    public BizBillDetailBean analyse(String content) {
        BizBillDetailBean ret = new BizBillDetailBean();
        String[] data = StringUtils.split(content, AlipayConstants.COLUMN_SPLIT);
        String bizType = data[2].trim();
        String tradeNo = data[0].trim();
        String orderNo = data[1].trim();
        LocalDateTime createTime = LocalDateTime.parse(data[4].trim(), AlipayConstants.ALIPAY_TIME_FORMATTER);
        LocalDateTime finishTime = LocalDateTime.parse(data[5].trim(), AlipayConstants.ALIPAY_TIME_FORMATTER);
        BigDecimal totalAmount = new BigDecimal(data[11].trim());
        BigDecimal realIncome = new BigDecimal(data[12].trim());
        if (AlipayConstants.BIZ_TYPE_REFUND.equals(bizType)) {
            ret.setRefundNo(data[21].trim());
        }
        ret.setBizType(bizType);
        ret.setTradeNo(tradeNo);
        ret.setOrderNo(orderNo);
        ret.setCreateTime(createTime);
        ret.setFinishTime(finishTime);
        ret.setTotalAmount(totalAmount);
        ret.setRealIncome(realIncome);
        return ret;
    }
}
