package com.shinelon.deathknight.es.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shinelon.deathknight.DeathknightApplicationTests;

/**
 * *
 *
 * @author syq
 */
public class EsSearchOptTest extends DeathknightApplicationTests {

  @Autowired private RestHighLevelClient restHighLevelClient;

  @Test
  public void searchTest() {
    SearchRequest searchRequest = new SearchRequest("posts");
    Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1));
    searchRequest.scroll(scroll);
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    //    sourceBuilder.query(QueryBuilders.termQuery("user", "kimchy"));
    sourceBuilder.query(QueryBuilders.matchAllQuery());
    sourceBuilder.from(0);
    sourceBuilder.size(5);
    sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
    searchRequest.source(sourceBuilder);
    try {
      SearchResponse searchResponse =
          restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
      SearchHits hits = searchResponse.getHits();
      String scrollId = searchResponse.getScrollId();
      SearchHit[] hits2 = hits.getHits();
      for (SearchHit searchHit : hits2) {
        logger.info("hit:{}", searchHit);
      }
      SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
      scrollRequest.scroll(TimeValue.timeValueSeconds(30));
      SearchResponse searchScrollResponse =
          restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
      logger.info("searchScrollResponse:{}", searchScrollResponse);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }
}
