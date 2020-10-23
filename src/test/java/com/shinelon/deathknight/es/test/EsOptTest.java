package com.shinelon.deathknight.es.test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.DocWriteRequest.OpType;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.client.core.TermVectorsResponse.TermVector;
import org.elasticsearch.client.core.TermVectorsResponse.TermVector.FieldStatistics;
import org.elasticsearch.client.core.TermVectorsResponse.TermVector.Term;
import org.elasticsearch.client.core.TermVectorsResponse.TermVector.Token;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shinelon.deathknight.DeathknightApplicationTests;

public class EsOptTest extends DeathknightApplicationTests {

  @Autowired private RestHighLevelClient restHighLevelClient;

  @Test
  public void initTest() {
    String string = restHighLevelClient.toString();
    logger.info(string);
  }

  @Test
  public void index() {
    Map<String, Object> jsonMap = new HashMap<>();
    jsonMap.put("user", "kimchy");
    jsonMap.put("postDate", new Date());
    jsonMap.put("message", "trying out Elasticsearch");
    IndexRequest indexRequest =
        new IndexRequest("posts").id("1").source(jsonMap).opType(OpType.CREATE);
    try {
      IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
      logger.info("indexResponse:{}", indexResponse);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void get1Test() {
    FetchSourceContext fetchSourceContext =
        new FetchSourceContext(true, new String[] {"message"}, null);
    GetRequest getRequest = new GetRequest("posts").id("1").fetchSourceContext(fetchSourceContext);
    try {
      GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
      logger.info("getResponse:{}", getResponse);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void get2Test() {
    GetRequest getRequest = new GetRequest("posts", "1");
    getRequest.fetchSourceContext(new FetchSourceContext(false));
    getRequest.storedFields("message");
    try {
      GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
      logger.info("getResponse:{}", getResponse);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void get3test() {
    GetRequest getRequest = new GetRequest("posts").id("1");

    try {
      GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
      logger.info("getResponse:{}", getResponse);
      Object updatedObj = getResponse.getSource().get("updated");
      logger.info("getResponse:{}", updatedObj);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void delTest() {
    DeleteRequest deleteRequest = new DeleteRequest("posts", "1");
    try {
      DeleteResponse deleteResponse =
          restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
      logger.info("deleteRequest:{}", deleteResponse);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void update1Test() {
    Map<String, Object> jsonMap = new HashMap<>();
    //    jsonMap.put("updated", new Date());
    //    jsonMap.put("reason", "daily update");
    jsonMap.put("count", 1);
    UpdateRequest updateRequest = new UpdateRequest("posts", "1").doc(jsonMap);
    try {
      UpdateResponse updateResponse =
          restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
      logger.info("updateResponse:{}", updateResponse);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void update2Test() {
	  Map<String, Object> jsonMap = new HashMap<>();
    jsonMap.put("count", 2);
    Script inline =
        new Script(
            ScriptType.INLINE,
            "painless",
            "if (ctx._source.containsKey('count')){ ctx._source.count += params.count } else { ctx._source.count = 0}",
            jsonMap);
    UpdateRequest updateRequest = new UpdateRequest("posts", "2").script(inline).retryOnConflict(3);
    try {
      UpdateResponse updateResponse =
          restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
      logger.info("updateResponse:{}", updateResponse);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void termVectorsTest() {
    TermVectorsRequest termVectorsRequest = new TermVectorsRequest("posts", "1");
    termVectorsRequest.setFields("user");
    try {
      TermVectorsResponse termvectorsResponse =
          restHighLevelClient.termvectors(termVectorsRequest, RequestOptions.DEFAULT);
      logger.info("termvectorsResponse:{}", termvectorsResponse.getFound());
      List<TermVector> termVectorsList = termvectorsResponse.getTermVectorsList();
      for (TermVector termVector : termVectorsList) {
        String fieldName = termVector.getFieldName();
        logger.info("fieldName:{}", fieldName);
        FieldStatistics fieldStatistics = termVector.getFieldStatistics();
        logger.info("fieldStatistics:{}", fieldStatistics);
        List<Term> terms = termVector.getTerms();
        for (Term term : terms) {
          List<Token> tokens = term.getTokens();
          for (Token token : tokens) {
            String payload = token.getPayload();
            logger.info("payload:{}", payload);
          }
        }
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }
}
