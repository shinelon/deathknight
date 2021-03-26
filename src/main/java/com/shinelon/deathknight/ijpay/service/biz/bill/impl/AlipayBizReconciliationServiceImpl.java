package com.shinelon.deathknight.ijpay.service.biz.bill.impl;

import com.shinelon.deathknight.ijpay.bean.BizBillDetailBean;
import com.shinelon.deathknight.ijpay.bean.OrderBean;
import com.shinelon.deathknight.ijpay.constants.AlipayConstants;
import com.shinelon.deathknight.ijpay.enums.OrderStatusEnums;
import com.shinelon.deathknight.ijpay.service.biz.bill.BizReconciliationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shinelon
 */
@Slf4j
@Service
public class AlipayBizReconciliationServiceImpl implements BizReconciliationService {

    private static final int DEFAULT_BATCH_SIZE = 500;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static String getSetKey(String keyPrefix, LocalDate date, String... exts) {
        String dateStr = AlipayConstants.ALIPAY_DATE_FORMATTER.format(date);
        StringBuilder keySb = new StringBuilder(64);
        keySb.append(keyPrefix);
        keySb.append(AlipayConstants.KEY_SPLIT);
        keySb.append(dateStr);
        for (String ext : exts) {
            keySb.append(AlipayConstants.KEY_SPLIT);
            keySb.append(ext);
        }
        return keySb.toString();
    }

    public void execute(LocalDate date) {
        List<OrderBean> orderBeanList = getOrderList(date);
        List<BizBillDetailBean> billList = getBillList(date);
        String ourSideTotalSetKey = getSetKey(AlipayConstants.RECONCILIATION, date, AlipayConstants.OUR_SIDE, AlipayConstants.TOTAL);
        String ourSideDiffSetKey = getSetKey(AlipayConstants.RECONCILIATION, date, AlipayConstants.OUR_SIDE, AlipayConstants.DIFF);
        String alipaySideTotalSetKey = getSetKey(AlipayConstants.RECONCILIATION, date, AlipayConstants.ALIPAY_SIDE, AlipayConstants.TOTAL);
        String alipaySideDiffSetKey = getSetKey(AlipayConstants.RECONCILIATION, date, AlipayConstants.ALIPAY_SIDE, AlipayConstants.DIFF);

        initOrderData(ourSideTotalSetKey, orderBeanList);
        initBillData(alipaySideTotalSetKey, billList);

        diffAndLog(ourSideTotalSetKey, alipaySideTotalSetKey, ourSideDiffSetKey);
        diffAndLog(alipaySideTotalSetKey, ourSideTotalSetKey, alipaySideDiffSetKey);

    }

    private void diffAndLog(String primaryKey, String otherKey, String destinationKey) {
        Long diffSize = diff(primaryKey, otherKey, destinationKey);
        if (diffSize > 0) {
            log.info("reconciliation.error.key:{},size:{}", destinationKey, diffSize);
            // and other alarm handle
        }

    }

    private Long diff(String primaryKey, String otherKey, String destinationKey) {
        return redisTemplate.opsForSet().differenceAndStore(primaryKey, otherKey, destinationKey);
    }

    private List<BizBillDetailBean> getBillList(LocalDate date) {
        //get bill from file
        return Collections.emptyList();
    }

    private List<OrderBean> getOrderList(LocalDate date) {
        // get order from db
        return Collections.emptyList();
    }

    public void initOrderData(String key, List<OrderBean> orderBeanList) {
        List<String> elementList = orderBeanList.stream().map(e -> elementKey(e)).collect(Collectors.toList());
        initData(key, elementList, DEFAULT_BATCH_SIZE);
    }

    public void initBillData(String key, List<BizBillDetailBean> billList) {
        List<String> elementList = billList.stream().map(e -> elementKey(e)).collect(Collectors.toList());
        initData(key, elementList, DEFAULT_BATCH_SIZE);
    }

    private void initData(String setKey, List<String> elementList, int batchSize) {
        if (CollectionUtils.isEmpty(elementList)) {
            return;
        }
        int size = elementList.size();
        int i = 1;
        List<String> batchList = new ArrayList<>(batchSize);
        for (String element : elementList) {
            batchList.add(element);
            if ((i % batchSize == 0) || i == size) {
                String[] strings = batchList.stream().toArray(String[]::new);
                redisTemplate.opsForSet().add(setKey, strings);
                batchList.clear();
            }
            i++;
        }
    }

    private String elementKey(OrderBean orderBean) {
        StringBuilder keySb = new StringBuilder(256);
        keySb.append(orderBean.getOrderNo());
        keySb.append(AlipayConstants.COLUMN_SPLIT);
        keySb.append(orderBean.getTradeNo());
        keySb.append(AlipayConstants.COLUMN_SPLIT);
        keySb.append(orderBean.getTotalAmount().toPlainString());
        if (OrderStatusEnums.REFUND_SUCCESS.getCode().equals(orderBean.getOrderStatus())) {
            keySb.append(AlipayConstants.COLUMN_SPLIT);
            keySb.append(orderBean.getRefundNo());
        }
        return keySb.toString();
    }

    private String elementKey(BizBillDetailBean billDetailBean) {
        StringBuilder keySb = new StringBuilder(256);
        keySb.append(billDetailBean.getOrderNo());
        keySb.append(AlipayConstants.COLUMN_SPLIT);
        keySb.append(billDetailBean.getTradeNo());
        keySb.append(AlipayConstants.COLUMN_SPLIT);
        keySb.append(billDetailBean.getTotalAmount().toPlainString());
        if (AlipayConstants.BIZ_TYPE_REFUND.equals(billDetailBean.getBizType())) {
            keySb.append(AlipayConstants.COLUMN_SPLIT);
            keySb.append(billDetailBean.getRefundNo());
        }
        return keySb.toString();
    }
}
