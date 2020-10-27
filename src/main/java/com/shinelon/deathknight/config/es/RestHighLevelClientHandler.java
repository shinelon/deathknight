package com.shinelon.deathknight.config.es;

import java.io.IOException;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * * 封装RestHighLevelClient 统一处理IOException
 *
 * @author syq
 */
@Component
public class RestHighLevelClientHandler {

  private static final Logger logger = LoggerFactory.getLogger(RestHighLevelClientHandler.class);

  @Autowired private RestHighLevelClient restHighLevelClient;

  public RestHighLevelClient getRestHighLevelClient() {
    return restHighLevelClient;
  }

  public <R extends ActionResponse, T extends ActionRequest> R exec(
      T request, RequestOptions options, EsFunction<T, RequestOptions, R> func) {
    try {
      R result = func.apply(request, options);
      return result;
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }
}
