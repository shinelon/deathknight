package com.shinelon.deathknight.ijpay.service.biz;

import cn.hutool.core.util.IdUtil;
import com.shinelon.deathknight.ijpay.enums.PayCodeEnum;
import com.shinelon.deathknight.ijpay.exception.PayException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/***
 * @author Shinelon
 */
public class PayBaseService {
    private static final String PAY_TOKEN_KEY_PREFIX = "pay:token:alipay:pc:";
    private static final long DEFAULT_TIMEOUT = 5 * 60L;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String generatePayToken(String userId) {
        String payToken = IdUtil.simpleUUID();
        String payTokenKey = payTokenKey(userId);
        stringRedisTemplate.opsForValue().set(payTokenKey, payToken, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return payToken;
    }


    public void checkPayToken(String userId, String token) {
        if (StringUtils.isBlank(token)) {
            throw new PayException(PayCodeEnum.ILLEGAL_PAY_TOKEN);
        }
        String payTokenKey = payTokenKey(userId);
        String value = stringRedisTemplate.opsForValue().get(payTokenKey);
        if (!token.equals(value)) {
            throw new PayException(PayCodeEnum.ILLEGAL_PAY_TOKEN);
        }
        stringRedisTemplate.delete(payTokenKey);
    }

    public String payTokenKey(String userId) {
        return PAY_TOKEN_KEY_PREFIX.concat(userId);
    }

}
