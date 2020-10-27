package com.shinelon.deathknight.es.test;

import java.io.IOException;
import java.util.Collections;

import org.elasticsearch.action.DocWriteRequest.OpType;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkItemResponse.Failure;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shinelon.deathknight.DeathknightApplicationTests;

public class EsBatchOptTest extends DeathknightApplicationTests {

  @Autowired private RestHighLevelClient restHighLevelClient;


  @Test
  public void bulk1test() {
    BulkRequest bulkRequest = new BulkRequest();
    bulkRequest.add(
        new IndexRequest("posts")
            .id("1")
            .opType(OpType.CREATE)
            .source(XContentType.JSON, "field", "foo"));
    bulkRequest.add(new IndexRequest("posts").id("2").source(XContentType.JSON, "field", "bar"));
    bulkRequest.add(new IndexRequest("posts").id("3").source(XContentType.JSON, "field", "baz"));
    try {
      BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
      logger.info("bulkResponse:{}", bulkResponse);
      BulkItemResponse[] items = bulkResponse.getItems();
      for (BulkItemResponse bulkItemResponse : items) {
        Failure failure = bulkItemResponse.getFailure();
        if (failure != null) {
          String message = bulkItemResponse.getFailureMessage();
          logger.info("failure:{},message:{}", failure, message);
        } else {
          DocWriteResponse response = bulkItemResponse.getResponse();
          String name = response.getResult().name();
          logger.info("name:{}", name);
        }
      }

    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void bulk2test() {
    BulkRequest bulkRequest = new BulkRequest();
    bulkRequest.add(new DeleteRequest("posts", "3"));
    bulkRequest.add(new UpdateRequest("posts", "2").doc(XContentType.JSON, "other", "test"));
    bulkRequest.add(new IndexRequest("posts").id("4").source(XContentType.JSON, "field", "baz"));
    try {
      BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
      logger.info("bulkResponse:{}", bulkResponse);
      BulkItemResponse[] items = bulkResponse.getItems();
      for (BulkItemResponse bulkItemResponse : items) {
        Failure failure = bulkItemResponse.getFailure();
        if (failure != null) {
          String message = bulkItemResponse.getFailureMessage();
          logger.info("failure:{},message:{}", failure, message);
        } else {
          DocWriteResponse response = bulkItemResponse.getResponse();
          String name = response.getResult().name();
          logger.info("name:{}", name);
        }
      }

    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void mulitGetTest() {
    MultiGetRequest multiGetRequest = new MultiGetRequest();
    multiGetRequest.add(new MultiGetRequest.Item("posts", "1"));
    multiGetRequest.add(new MultiGetRequest.Item("posts", "2"));
    try {
      MultiGetResponse mGetResponse =
          restHighLevelClient.mget(multiGetRequest, RequestOptions.DEFAULT);
      MultiGetItemResponse[] responses = mGetResponse.getResponses();
      for (MultiGetItemResponse multiGetItemResponse : responses) {
        logger.info("item.response:{}", multiGetItemResponse.getResponse().getSourceAsString());
      }

    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void updateByQueryTest() {
    UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest("posts");
    updateByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
    Script inline =
        new Script(
            ScriptType.INLINE,
            "painless",
            "if (ctx._source.containsKey('count')){ ctx._source.count += 1 } else { ctx._source.count = 0}",
            Collections.emptyMap());
    updateByQueryRequest.setScript(inline);
    try {
      BulkByScrollResponse bulkByScrollResponse =
          restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
      logger.info("bulkByScrollResponse:{}", bulkByScrollResponse);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void removeFieldByQueryTest() {
    UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest("posts");
    updateByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
    Script inline =
        new Script(
            ScriptType.INLINE, "painless", "ctx._source.remove(\"count\")", Collections.emptyMap());
    updateByQueryRequest.setScript(inline);
    try {
      BulkByScrollResponse bulkByScrollResponse =
          restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
      logger.info("bulkByScrollResponse:{}", bulkByScrollResponse);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void deleteByQueryRequestTest() {
    DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("restaurant");
    deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
    try {
      BulkByScrollResponse deleteByQueryResponse =
          restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
      logger.info("deleteByQueryResponse:{}", deleteByQueryResponse);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }
}
