package com.shinelon.deathknight.ijpay.service.biz.pay;

import cn.hutool.core.util.IdUtil;
import com.shinelon.deathknight.ijpay.enums.PayCodeEnum;
import com.shinelon.deathknight.ijpay.exception.PayException;
import com.shinelon.deathknight.ijpay.handler.PaySeqNoHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

/***
 * @author Shinelon
 */
public class BasePayService {
    private static final String PAY_TOKEN_KEY_PREFIX = "trade:token:alipay:";
    private static final long DEFAULT_TIMEOUT = 5 * 60L;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private PaySeqNoHandler paySeqNoHandler;


    public String nextSeqNo() {
        return paySeqNoHandler.nextNo();
    }

    public String generateToken(String userId) {
        String payToken = IdUtil.simpleUUID();
        String payTokenKey = tokenKey(userId);
        redisTemplate.opsForValue().set(payTokenKey, payToken, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return payToken;
    }


    public void checkToken(String userId, String token) {
        if (StringUtils.isBlank(token)) {
            throw new PayException(PayCodeEnum.ILLEGAL_PAY_TOKEN);
        }
        String payTokenKey = tokenKey(userId);
        String value = redisTemplate.opsForValue().get(payTokenKey);
        if (!token.equals(value)) {
            throw new PayException(PayCodeEnum.ILLEGAL_PAY_TOKEN);
        }
        redisTemplate.delete(payTokenKey);
    }

    public String tokenKey(String userId) {
        return PAY_TOKEN_KEY_PREFIX.concat(userId);
    }

}
