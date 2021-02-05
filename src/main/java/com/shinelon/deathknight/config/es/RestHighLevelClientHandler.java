package com.shinelon.deathknight.config.es;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * * 封装RestHighLevelClient 统一处理IOException
 *
 * @author syq
 */
@Component
@ConditionalOnProperty(prefix = "enable", name = "es", havingValue = "true", matchIfMissing = false)
public class RestHighLevelClientHandler {

  private static final Logger logger = LoggerFactory.getLogger(RestHighLevelClientHandler.class);

  @Autowired
  private RestHighLevelClient restHighLevelClient;

  public RestHighLevelClient getRestHighLevelClient() {
    return restHighLevelClient;
  }

  /*
   * <p>Description: </p>
   *
   * @param request
   * @param options
   * @param func
   * @param <R>
   * @param <T>
   * @return
   */
  public <R extends ActionResponse, T extends ActionRequest> R exec(
      T request, RequestOptions options, EsFunction<T, RequestOptions, R> func) {
    try {
      return func.apply(request, options);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }
}
