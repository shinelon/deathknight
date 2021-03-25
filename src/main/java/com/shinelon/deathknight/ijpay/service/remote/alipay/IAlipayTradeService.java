package com.shinelon.deathknight.ijpay.service.remote.alipay;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.util.json.JSONWriter;
import com.shinelon.deathknight.ijpay.service.remote.ITradeRemoteService;

/**
 * @author Shinelon
 * @date 2021-03-25 11:46
 */
public interface IAlipayTradeService extends ITradeRemoteService {
    /***
     * 成功code
     */
    String SUCCESS_CODE = "10000";

    /***
     * isSuccessCode
     * @param code
     * @return
     */
    default boolean isSuccessCode(String code) {
        return SUCCESS_CODE.equals(code);
    }

    /***
     * toBodyJson
     *
     * @param alipayObject
     * @return
     */
    default String toBodyJson(AlipayObject alipayObject) {
        return new JSONWriter().write(alipayObject, true);
    }
}
