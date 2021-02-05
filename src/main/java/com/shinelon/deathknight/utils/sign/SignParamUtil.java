package com.shinelon.deathknight.utils.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

//@formatter:off

/**
 * 将JSON转为待签名MAP
 * <p>
 * 格式如下:
 * String key = ${fileld}[#${index}].${subFileld}
 * String value = String.valueOf(obj)
 * <p>
 * 示例:
 * 原始JSON
 * {
 * "keyLong":1,
 * "keyStr":"2str",
 * "keyArray":[
 * {
 * "ary1":"ary11"
 * },
 * {
 * "ary1":"ary11"
 * },
 * {
 * "aryary34":[
 * {
 * "ary4":"ary44",
 * "ary3":"ary33"
 * },
 * {
 * "ary4":"ary44",
 * "ary3":"ary33"
 * }
 * ]
 * }
 * ]
 * }
 * <p>
 * 转化为MAP的json格式
 * <p>
 * {
 * "keyArray#0.ary1":"ary11",
 * "keyArray#2.aryary34#1.ary4":"ary44",
 * "keyArray#2.aryary34#1.ary3":"ary33",
 * "keyLong":"1",
 * "keyArray#2.aryary34#0.ary4":"ary44",
 * "keyArray#1.ary1":"ary11",
 * "keyArray#2.aryary34#0.ary3":"ary33",
 * "keyStr":"2str"
 * }
 *
 * @author Shinelon
 * @date 2021-02-03
 */
//@formatter:on
@Slf4j
public class SignParamUtil {


    /***
     * 将JSONObject按照签名格式进行封装
     * @param jsonObject
     * @return
     */
    public static Map<String, String> jsonToParamsMap(JSONObject jsonObject) {
        return jsonToParamsMap(jsonObject, null, null);
    }


    public static Map<String, String> jsonToParamsMap(JSONObject jsonObject, String field, Integer index) {
        if (null == jsonObject) {
            return Collections.emptyMap();
        }
        Map<String, String> paramsMap = new HashMap<>(jsonObject.size() * 2);
        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            StringBuffer keySb = new StringBuffer(16);
            if (!StringUtils.isBlank(field)) {
                keySb.append(field);
                if (null != index) {
                    keySb.append("#").append(index);
                }
                keySb.append(".");
            }
            keySb.append(entry.getKey());

            String key = keySb.toString();
            Object entryValue = entry.getValue();
            if (null == entryValue) {
                continue;
            }
            if (entryValue instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) entryValue;
                for (int i = 0; i < jsonArray.size(); i++) {
                    Object object = jsonArray.get(i);
                    if (object instanceof JSONObject) {
                        paramsMap.putAll(jsonToParamsMap((JSONObject) object, key, i));
                    } else {
                        paramsMap.put(key + "#" + i, String.valueOf(object));
                    }
                }
                continue;
            }
            if (entryValue instanceof JSONObject) {
                JSONObject jsonObj = (JSONObject) entryValue;
                paramsMap.putAll(jsonToParamsMap(jsonObj, key, null));
                continue;
            }
            paramsMap.put(key, String.valueOf(entryValue));
        }
        return paramsMap;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tags", Arrays.asList("tags1", "tags2"));
        jsonObject.put("keyLong", 1L);
        JSONArray jsonArray = new JSONArray();
        Map<String, Object> map = new HashMap<>(16);
        map.put("ary1", "ary11");
        jsonArray.add(new JSONObject(map));
        jsonArray.add(new JSONObject(map));

        JSONArray jsonArray2 = new JSONArray();
        Map<String, Object> map2 = new HashMap<>(16);
        map2.put("ary3", "ary33");
        map2.put("ary4", "ary44");
        jsonArray2.add(new JSONObject(map2));
        jsonArray2.add(new JSONObject(map2));
        JSONObject jobj = new JSONObject();
        jobj.put("aryary34", jsonArray2);
        jsonArray.add(jobj);

        jsonObject.put("keyArray", jsonArray);
        jsonObject.put("keyStr", "2str");
        String jsonString = jsonObject.toJSONString();
        log.info("json:{}", jsonString);
        Map<String, String> stringStringMap = jsonToParamsMap(JSONObject.parseObject(jsonString));
        log.info("map:{}", stringStringMap);
        log.info("mapJson:{}", JSON.toJSONString(stringStringMap));
    }

}
