package com.shinelon.deathknight.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class IndexController {

    @RequestMapping("/")
    public String index() {
        log.info("deathkight is startup!");
        return "deathkight is startup!";
    }
}
