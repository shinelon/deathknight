package com.shinelon.deathknight.ijpay.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author Shinelon
 */
@Component
public class PaySeqNoHandler {


    private static final String SEQ_KEY_PREFIX = "pay_seq_key:";
    private static final long SEQ_KEY_TIME_OUT_HOURS = 25L;
    private static final long CHECK_EXPIRE_VALUE = 3;
    private static final int PAD_SIZE = 7;
    private static final DateTimeFormatter SEQ_KEY_DTF = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter SEQ_VALUE_DTF = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * yyyyMMddHHmmssSSS(17位)+incr(7位)
     * <p>
     * 1.每毫秒产生千万级数据才可能产生重复
     * <p>
     * 2.当日产序列号千万以下，即使多个实例的时间不同步也不会重复。
     * <p>
     * 3.序列号日期有序，且天内有序
     * <p>
     * 4.多个实例时间误差不能超过1天
     * <p>
     * 5.适合核心业务使用，不要全局使用
     *
     * @return
     */
    public String nextNo() {
        LocalDateTime now = LocalDateTime.now();
        Long no = incr(now);
        String noStr = Long.toString(no);
        String padNo = StringUtils.leftPad(noStr, PAD_SIZE, "0");
        String fixedLengthNo = StringUtils.substring(padNo, padNo.length() - PAD_SIZE, PAD_SIZE);
        return now.format(SEQ_VALUE_DTF).concat(fixedLengthNo);
    }

    private String getSeqKey(LocalDateTime dateTime) {
        return SEQ_KEY_PREFIX.concat(dateTime.format(SEQ_KEY_DTF));
    }

    private Long incr(LocalDateTime dateTime) {
        String seqKey = getSeqKey(dateTime);
        Long increment = redisTemplate.opsForValue().increment(seqKey);
        if (increment <= CHECK_EXPIRE_VALUE) {
            Long expire = redisTemplate.getExpire(seqKey);
            if (expire < 0L) {
                redisTemplate.expire(seqKey, SEQ_KEY_TIME_OUT_HOURS, TimeUnit.HOURS);
            }
        }
        return increment;
    }

}
