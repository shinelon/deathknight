package com.shinelon.deathknight.ijpay.bean;

import lombok.Data;

import java.util.List;

/**
 * 业务账单分析结果
 *
 * @author Shinelon
 */
@Data
public class BizBillAnalyseResult {
    private Boolean isSuccess;
    private List<BizBillDetailBean> billList;
}
