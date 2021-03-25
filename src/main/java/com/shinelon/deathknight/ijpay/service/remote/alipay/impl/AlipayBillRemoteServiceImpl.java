package com.shinelon.deathknight.ijpay.service.remote.alipay.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.ijpay.alipay.AliPayApi;
import com.shinelon.deathknight.ijpay.dto.alipay.AlipayBillReq;
import com.shinelon.deathknight.ijpay.dto.alipay.AlipayBillRes;
import com.shinelon.deathknight.ijpay.service.remote.alipay.IAlipayBillRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Shinelon
 * @date 2021-03-25 14:38
 */
@Service
@Slf4j
public class AlipayBillRemoteServiceImpl implements IAlipayBillRemoteService {

    @Override
    public AlipayBillRes downloadUrl(AlipayBillReq alipayBillReq) {
        AlipayDataDataserviceBillDownloadurlQueryModel model = convertUrlQueryModel(alipayBillReq);
        try {
            AlipayDataDataserviceBillDownloadurlQueryResponse urlQueryResponse = AliPayApi.billDownloadUrlQueryToResponse(model);
            return convertAlipayBillRes(urlQueryResponse);
        } catch (AlipayApiException e) {
            log.error("downloadUrl.errorMsg:{},errorCode:{}", e.getErrMsg(), e.getErrCode());
            log.error(e.getMessage(), e);
        }
        return failedAlipayBillRes();
    }

    public AlipayBillRes convertAlipayBillRes(AlipayDataDataserviceBillDownloadurlQueryResponse urlQueryResponse) {
        AlipayBillRes res = new AlipayBillRes();
        res.setBillDownloadUrl(urlQueryResponse.getBillDownloadUrl());
        res.setResponseBody(urlQueryResponse.getBody());
        return res;
    }

    private AlipayDataDataserviceBillDownloadurlQueryModel convertUrlQueryModel(AlipayBillReq alipayBillReq) {
        AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
        model.setBillDate(alipayBillReq.getBillDate());
        model.setBillType(alipayBillReq.getBillType());
        alipayBillReq.setRequestBody(toBodyJson(model));
        return model;
    }

    private AlipayBillRes failedAlipayBillRes() {
        AlipayBillRes res = new AlipayBillRes();
        res.setIsSuccess(false);
        return res;
    }
}
