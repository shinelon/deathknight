package com.shinelon.deathknight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;

@SpringBootApplication(exclude = {ElasticsearchDataAutoConfiguration.class})
public class DeathknightApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeathknightApplication.class, args);
    }

}
