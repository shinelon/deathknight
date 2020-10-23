package com.shinelon.deathknight;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = DeathknightApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class DeathknightApplicationTests {
	
  protected Logger logger = LoggerFactory.getLogger(getClass());
  
  
  @Test
  void contextLoads() {
  }
}
