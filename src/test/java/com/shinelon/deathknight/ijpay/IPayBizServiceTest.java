package com.shinelon.deathknight.ijpay;

import com.shinelon.deathknight.DeathknightApplicationTests;
import com.shinelon.deathknight.ijpay.service.biz.impl.PayBizServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/***
 * @author Shinelon
 */
public class IPayBizServiceTest extends DeathknightApplicationTests {
    @Autowired
    private PayBizServiceImpl payBizService;

    @Test
    public void payTest() {
        payBizService.pay("alipay");
        payBizService.pay("wechat");
//        payBizService.pay("123");
    }
}
