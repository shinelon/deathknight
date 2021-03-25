package com.shinelon.deathknight.ijpay.service.remote.wechat;

import cn.hutool.json.JSONUtil;
import com.shinelon.deathknight.ijpay.service.remote.ITradeRemoteService;

/**
 * @author Shinelon
 * @date 2021-03-25 14:36
 */
public interface IWechatTradeRemote extends ITradeRemoteService {

    /***
     * 成功code
     */
    int SUCCESS_CODE = 200;

    /***
     * isSuccessCode
     * @param code
     * @return
     */
    default boolean isSuccessCode(int code) {
        return SUCCESS_CODE == code;
    }

    /**
     * toBodyJson
     *
     * @return
     */
    default String toBodyJson(Object params) {
        return JSONUtil.toJsonStr(params);
    }
}
