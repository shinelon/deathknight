package com.shinelon.deathknight.ijpay.service.remote.wechat;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shinelon.deathknight.ijpay.service.remote.ITradeRemoteService;

/**
 * @author Shinelon
 * @date 2021-03-25 14:36
 */
public interface IWechatTradeRemote extends ITradeRemoteService {

    /***
     * 成功code 有返回值
     */
    int SUCCESS_CODE = 200;
    /**
     * 成功code 无返回值
     */
    int SUCCESS_NO_DATA_CODE = 204;
    /***
     * 人民币
     */
    String CNY = "CNY";

    /***
     * isSuccessCode
     * @param code
     * @return
     */
    default boolean isSuccessCode(int code) {
        return SUCCESS_CODE == code || SUCCESS_NO_DATA_CODE == code;
    }

    /***
     * toBodyJson
     * @param params
     * @return
     */
    default String toBodyJson(Object params) {
        return JSONUtil.toJsonStr(params);
    }


    /***
     * getStringFromJson
     * @param params
     * @param field
     * @return
     */
    default String getStringFromJson(String params, String field) {
        JSONObject jsonObject = JSON.parseObject(params);
        return jsonObject.getString(field);
    }
}
