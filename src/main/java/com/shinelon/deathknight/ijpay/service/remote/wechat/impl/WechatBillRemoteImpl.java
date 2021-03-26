package com.shinelon.deathknight.ijpay.service.remote.wechat.impl;

import cn.hutool.json.JSONUtil;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethod;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxApiType;
import com.ijpay.wxpay.enums.WxDomain;
import com.ijpay.wxpay.model.DownloadBillModel;
import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatBillReq;
import com.shinelon.deathknight.ijpay.dto.remote.wechat.WechatBillRes;
import com.shinelon.deathknight.ijpay.service.remote.wechat.BaseWechatTradeRemote;
import com.shinelon.deathknight.ijpay.service.remote.wechat.IWechatBillRemote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Shinelon
 * @date 2021-03-26 14:58
 */
@Service
@Slf4j
public class WechatBillRemoteImpl extends BaseWechatTradeRemote implements IWechatBillRemote {


    @Override
    public WechatBillRes downloadUrl(WechatBillReq wechatBillReq) {
        DownloadBillModel downloadBillModel = convertDownloadBillModel(wechatBillReq);
        try {
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethod.GET,
                    WxDomain.CHINA.toString(),
                    WxApiType.TRADE_BILL.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    JSONUtil.toJsonStr(downloadBillModel)
            );
            assertVerifySignatureTrue(response);
            return convertWechatBillRes(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return failedWechatBillRes();
    }

    private WechatBillRes convertWechatBillRes(IJPayHttpResponse response) {
        WechatBillRes res = new WechatBillRes();
        boolean isSuccess = isSuccessCode(response.getStatus());
        String body = response.getBody();
        res.setIsSuccess(isSuccess);
        res.setResponseBody(body);
        res.setBillDownloadUrl(getStringFromJson(body, "download_url"));
        return res;
    }

    private DownloadBillModel convertDownloadBillModel(WechatBillReq wechatBillReq) {
        return DownloadBillModel.builder()
                .bill_date(wechatBillReq.getBillDate())
                .bill_type(wechatBillReq.getBillType())
                .tar_type("GZIP").build();
    }

    private WechatBillRes failedWechatBillRes() {
        WechatBillRes res = new WechatBillRes();
        res.setIsSuccess(false);
        return res;
    }
}
