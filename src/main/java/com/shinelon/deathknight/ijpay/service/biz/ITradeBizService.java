package com.shinelon.deathknight.ijpay.service.biz;

import com.shinelon.deathknight.ijpay.enums.OrderStatusEnums;

/**
 * @author Shinelon
 * @date 2021-03-26 15:58
 */
public interface ITradeBizService {

    /***
     * 付款状态
     * 交易状态：
     * WAIT_BUYER_PAY（交易创建，等待买家付款）、
     * TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
     * TRADE_SUCCESS（交易支付成功）、
     * TRADE_FINISHED（交易结束，不可退款）
     * 退款状态
     * 无
     * @param tradeStatus
     * @return
     */
    default OrderStatusEnums alipayTradeStatusToOrderStatus(String tradeStatus) {
        OrderStatusEnums enums = null;
        switch (tradeStatus) {
            case "WAITBUYER_PAY":
                enums = OrderStatusEnums.PAYING;
                break;
            case "TRADE_CLOSED":
                enums = OrderStatusEnums.PAY_FAILED;
                break;
            case "TRADE_SUCCESS":
                enums = OrderStatusEnums.PAY_SUCCESS;
                break;
            case "TRADE_FINISHED":
                enums = OrderStatusEnums.PAY_SUCCESS;
                break;
            default:
                enums = OrderStatusEnums.PAY_FAILED;
        }
        return enums;
    }

    /***
     * 付款状态
     * SUCCESS：支付成功
     * REFUND：转入退款
     * NOTPAY：未支付
     * CLOSED：已关闭
     * REVOKED：已撤销（付款码支付）
     * USERPAYING：用户支付中（付款码支付）
     * PAYERROR：支付失败(其他原因，如银行返回失败)
     * ACCEPT：已接收，等待扣款
     * @param tradeStatus
     * @return
     */
    default OrderStatusEnums wechatTradeStatusToOrderStatus(String tradeStatus) {
        OrderStatusEnums enums = null;
        switch (tradeStatus) {
            case "SUCCESS":
                enums = OrderStatusEnums.PAY_SUCCESS;
                break;
            case "REFUND":
                enums = OrderStatusEnums.PAY_SUCCESS;
                break;
            case "NOTPAY":
                enums = OrderStatusEnums.PAYING;
                break;
            case "CLOSED":
                enums = OrderStatusEnums.PAY_FAILED;
                break;
            case "REVOKED":
                enums = OrderStatusEnums.PAY_FAILED;
                break;
            case "USERPAYING":
                enums = OrderStatusEnums.PAYING;
                break;
            case "PAYERROR":
                enums = OrderStatusEnums.PAY_FAILED;
                break;
            default:
                enums = OrderStatusEnums.REFUND_FAILED;
        }
        return enums;
    }

    /***
     * 退款状态
     * SUCCESS：退款成功
     * CLOSED：退款关闭
     * PROCESSING：退款处理中
     * ABNORMAL：退款异常
     * @param tradeStatus
     * @return
     */
    default OrderStatusEnums wechatRefundStatusToOrderStatus(String tradeStatus) {
        OrderStatusEnums enums = null;
        switch (tradeStatus) {
            case "SUCCESS":
                enums = OrderStatusEnums.REFUND_SUCCESS;
                break;
            case "PROCESSING":
                enums = OrderStatusEnums.REFUNDING;
                break;
            case "CLOSED":
                enums = OrderStatusEnums.REFUND_FAILED;
                break;
            case "ABNORMAL":
                enums = OrderStatusEnums.REFUND_FAILED;
                break;
            default:
                enums = OrderStatusEnums.REFUND_FAILED;
        }
        return enums;
    }

}
