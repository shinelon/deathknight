package com.shinelon.deathknight.spel;

import com.shinelon.deathknight.DeathknightApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;

/***
 *
 */
public class SpelTest extends DeathknightApplicationTests {
    @Autowired
    private SpelService spelService;

    @Test
    public void test() {
        LocalDateTime now = LocalDateTime.now();
        String str = "123";
        Long l = 1L;
        ParamDemo paramDemo = new ParamDemo();
        ParamDemo.Bean bean = new ParamDemo.Bean();
        bean.setBeanParamLong(22L);
        bean.setBeanParamString("Str");
        paramDemo.setBeanList(Arrays.asList(bean, bean));
        spelService.handle(now, str, l, paramDemo);
    }
}
