package com.shinelon.deathknight.ijpay.constants;

import java.time.format.DateTimeFormatter;

/**
 * @author Shinelon
 */
public class AlipayConstants {
    /***
     * 支付宝业务账单明细文件名
     */
    public static final String BIZ_BILL_FILE_NAME = "业务明细.csv";
    /***
     * 账单文件注释说明符号
     */
    public static final String BILL_FILE_COMMENT_SYMBOL = "#";
    /***
     *业务账单明细 表头第一列
     */
    public static final String CONTENT_HEAD_LINE_1 = "支付宝交易号";
    /**
     * 列分割符
     */
    public static final String COLUMN_SPLIT = ",";
    /***
     * 时间格式
     */
    public static final String ALIPAY_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /***
     * 时间格式
     */
    public static final DateTimeFormatter ALIPAY_TIME_FORMATTER = DateTimeFormatter.ofPattern(ALIPAY_TIME_PATTERN);

    /***
     * 时间格式
     */
    public static final String ALIPAY_DATE_PATTERN = "yyyy-MM-dd";
    /***
     * 时间格式
     */
    public static final DateTimeFormatter ALIPAY_DATE_FORMATTER = DateTimeFormatter.ofPattern(ALIPAY_DATE_PATTERN);
    /***
     *退款
     */
    public static final String BIZ_TYPE_REFUND = "退款";
    /***
     * 交易
     */
    public static final String BIZ_TYPE_TRADE = "交易";
    /**
     * redis key 分隔符
     */
    public static final String KEY_SPLIT = ":";
    /**
     * 对账
     */
    public static final String RECONCILIATION = "reconciliation";
    /***
     *我方订单
     */
    public static final String OUR_SIDE = "our_side";
    /***
     *支付宝账单
     */
    public static final String ALIPAY_SIDE = "alipay_side";
    /***
     * 全量
     */
    public static final String TOTAL = "total";
    /***
     * 差异
     */
    public static final String DIFF = "diff";

}
