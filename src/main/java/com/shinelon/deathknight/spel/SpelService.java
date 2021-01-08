package com.shinelon.deathknight.spel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author shinelon
 */
@Service
public class SpelService {

    private static final Logger logger = LoggerFactory.getLogger(SpelService.class);

    @Spel(keySpel = "#arg1", valueSpel = "#paramDemo.beanList[0].beanParamLong")
    public String handle(LocalDateTime arg1, String arg2, Long agr3, ParamDemo paramDemo) {

        return "ok";
    }
}
