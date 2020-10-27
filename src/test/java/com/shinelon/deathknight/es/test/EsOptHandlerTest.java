/**  
* <p>Title: EsOptHandlerTest.java</p>  
* <p>Description: </p>  
* @author shinelon  
* @date 2020年10月27日  
*/ 
package com.shinelon.deathknight.es.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.DocWriteRequest.OpType;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shinelon.deathknight.DeathknightApplicationTests;
import com.shinelon.deathknight.config.es.RestHighLevelClientHandler;

public class EsOptHandlerTest extends DeathknightApplicationTests {

  @Autowired private RestHighLevelClientHandler handler;

  @Test
  public void index() {
    Map<String, Object> jsonMap = new HashMap<>();
    jsonMap.put("user", "kimchy");
    jsonMap.put("postDate", new Date());
    jsonMap.put("message", "trying out Elasticsearch");
    IndexRequest indexRequest =
        new IndexRequest("posts").id("1").source(jsonMap).opType(OpType.CREATE);
    IndexResponse indexResponse =
        handler.exec(
            indexRequest,
            RequestOptions.DEFAULT,
            (t, u) -> handler.getRestHighLevelClient().index(t, u));
    logger.info("indexResponse:{}", indexResponse);
  }
}
