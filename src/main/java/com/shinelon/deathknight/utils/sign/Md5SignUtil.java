package com.shinelon.deathknight.utils.sign;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/***
 * 引用加密包
 *
 *         <dependency>
 *             <groupId>cn.hutool</groupId>
 *             <artifactId>hutool-crypto</artifactId>
 *             <version>5.5.2</version>
 *         </dependency>
 *
 *
 *
 * @author shinelon
 */
public class Md5SignUtil {
    private static final Logger logger = LoggerFactory.getLogger(Md5SignUtil.class);

    public static String sign(Map<String, String> params, String key) {
        Assert.hasText(key, "'key' must not be empty");
        Assert.notEmpty(params, "params must not be empty");
        String md5Hex = DigestUtil.md5Hex(waitSignStr(params, key));
        return md5Hex.toUpperCase();
    }

    public static boolean verifySign(Map<String, String> params, String key, String sign) {
        Assert.hasText(key, "'key' must not be empty");
        Assert.hasText(sign, "'sign' must not be empty");
        Assert.notEmpty(params, "params must not be empty");
        String md5Hex = DigestUtil.md5Hex(waitSignStr(params, key)).toUpperCase();
        return sign.equals(md5Hex);
    }

    public static boolean verifySignWithTimestamp(Map<String, String> params, String key, String sign,
                                                  long intervalSeconds) {
        String timestampStr = params.getOrDefault("timestamp", String.valueOf(0));
        long currentSeconds = System.currentTimeMillis() / 1000;
        if (currentSeconds - Long.parseLong(timestampStr) > intervalSeconds) {
            return false;
        }
        return verifySign(params, key, sign);
    }


    private static String waitSignStr(Map<String, String> params, String key) {
        Map<String, String> sortMap = new TreeMap<>(params);
        StringBuilder waitSignSb = new StringBuilder(params.size() * 8);
        sortMap.forEach((k, v) -> {
            waitSignSb.append(k).append("=").append(v).append("&");
        });
        logger.debug("waitSignWithoutKey:{}", waitSignSb);
        waitSignSb.append("key=").append(key);
        return waitSignSb.toString();
    }

    public static void main(String[] args) {
        // 32位key
        String key = "192006250b4c09247ec02edce69f6a2d";
        Map<String, String> paramsMap = new HashMap<>(8);
        // 16位随机字符串增加解密难度 必填字段
        paramsMap.put("nonce_str", IdUtil.simpleUUID().substring(0, 16));
        // 时间戳(单位秒) 用来校验请求时间间隔 必填字段
        paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        // 业务参数
        paramsMap.put("appid", "1");
        paramsMap.put("mch_id", "2");

        String sign = Md5SignUtil.sign(paramsMap, key);
        logger.info("sign:{}", sign);
        logger.info("verifySign :{}", Md5SignUtil.verifySign(paramsMap, key, sign));
        logger.info("verifySignWithTimestamp :{}", Md5SignUtil.verifySignWithTimestamp(paramsMap, key, sign, 1));
        logger.info("verifySignWithTimestamp :{}", Md5SignUtil.verifySignWithTimestamp(paramsMap, key, sign, 0));
    }


}
