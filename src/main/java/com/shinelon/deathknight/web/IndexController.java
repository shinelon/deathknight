package com.shinelon.deathknight.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author Shinelon
 */
@RestController
@Slf4j
public class IndexController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/")
    public String index() {
        log.info("deathkight is startup!");
        return "deathkight is startup!";
    }

    @RequestMapping("/redis")
    public String redis() {
        return "randomKey:" + redisTemplate.randomKey();
    }
}
