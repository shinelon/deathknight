package com.shinelon.deathknight.demo;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class SeqTest {
    /***
     * 找出字符串中最长连续字符串
     */
    @Test
    public void seqSearch() {
        String str = "ddabcedefghioa";
        int maxLength = 0;
        int startIdx = 0;
        int endIdx = 0;
        for (int i = 0; i < str.length(); i++) {
            int j = i + 1;
            while (j < str.length()) {
                int current = str.charAt(j);
                int pre = str.charAt(j - 1);
                if (current - pre == 1) {
                    j++;
                    int tmpLength = j - i;
                    if (tmpLength > maxLength) {
                        startIdx = i;
                        endIdx = j;
                        maxLength = tmpLength;
                    }
                } else {
                    break;
                }
            }
        }
        System.out.println("maxLength:" + maxLength + " startIdx:" + startIdx + " endIdx:" + endIdx);
        System.out.println("string:" + str.substring(startIdx, endIdx + 1));
    }

    @Test
    public void seqNo() {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (int i = 0; i < 100; i++) {
            System.out.println(snowflake.nextId());
            System.out.println(snowflake.nextIdStr());
        }
    }

    @Test
    public void bigDecimalDemo() {
        BigDecimal b1 = new BigDecimal("50000.000");
        System.out.println(b1.toPlainString());
        System.out.println(b1.toEngineeringString());
        System.out.println(b1.toString());

        BigDecimal b2 = new BigDecimal(9999L);
        System.out.println(b2.toPlainString());
        System.out.println(b2.toEngineeringString());
        System.out.println(b2.toString());
    }

}
