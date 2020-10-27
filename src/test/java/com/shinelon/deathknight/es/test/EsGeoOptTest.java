package com.shinelon.deathknight.es.test;

import java.io.IOException;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkItemResponse.Failure;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.geometry.utils.Geohash;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shinelon.deathknight.DeathknightApplicationTests;
/**
 * * mapping创建后无法修改，所以最好是先创建mapping,插入数据
 *
 * @author syq
 */
public class EsGeoOptTest extends DeathknightApplicationTests {

  @Autowired private RestHighLevelClient restHighLevelClient;

  @Test
  public void initData() {
    BulkRequest bulkRequest = new BulkRequest();
    bulkRequest.add(
        new IndexRequest("restaurant")
            .id("1")
            .source(
                XContentType.JSON,
                "name",
                "Chipotle Mexican Grill",
                "location",
                "40.715, -74.011"));
    try {
      XContentBuilder restaurantBuilder =
          XContentFactory.jsonBuilder()
              .prettyPrint()
              .startObject()
              .field("name", "Pala Pizza")
              .startObject("location")
              .field("lat", 40.722D)
              .field("lon", "-73.989")
              .endObject()
              .endObject();
      bulkRequest.add(new IndexRequest("restaurant").id("2").source(restaurantBuilder));
    } catch (IOException e1) {
      logger.error(e1.getMessage(), e1);
    }

    bulkRequest.add(
        new IndexRequest("restaurant")
            .id("3")
            .source(
                "{\"name\":\"Mini Munchies Pizza\",\"location\":[-73.983,40.719]}",
                XContentType.JSON));

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
  /** * 在创建index过程中，添加mapping参数 */
  @Test
  public void geoIndexMappingTest() {
    CreateIndexRequest createIndexRequest = new CreateIndexRequest("restaurant");
    createIndexRequest.mapping(
        "{\"properties\":{\"name\":{\"type\":\"text\"},\"location\":{\"type\":\"geo_point\"}}}",
        XContentType.JSON);
    try {
      CreateIndexResponse response =
          restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
      logger.info("{}", response);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }
  /** * 使用_mapping方法设置 */
  @Test
  public void geoPutMappingTest() {
    PutMappingRequest putMappingRequest = new PutMappingRequest("restaurant");
    putMappingRequest.source(
        "{\"properties\":{\"name\":{\"type\":\"text\"},\"location\":{\"type\":\"geo_point\"}}}",
        XContentType.JSON);
    try {
      AcknowledgedResponse putMappingResponse =
          restHighLevelClient.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
      logger.info("{}", putMappingResponse);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void distanceTest() throws JSONException {
    GeoPoint srcPoint = new GeoPoint();
    srcPoint.reset(40.715D, -73.988D);
    // SearchRequest
    SearchRequest searchRequest = new SearchRequest("restaurant");
    // GeoDistanceQueryBuilder

    GeoDistanceQueryBuilder geoDistanceQuery = QueryBuilders.geoDistanceQuery("location");
    geoDistanceQuery.geoDistance(GeoDistance.PLANE);
    geoDistanceQuery.distance("2km", DistanceUnit.KILOMETERS);
    geoDistanceQuery.point(srcPoint);
    

    // SearchSourceBuilder
    SearchSourceBuilder ssb = new SearchSourceBuilder();
    ssb.query(geoDistanceQuery);
    // ssb.postFilter(geoDistanceQuery);
    // GeoDistanceSortBuilder
    GeoDistanceSortBuilder gdsb = new GeoDistanceSortBuilder("location", srcPoint);
    gdsb.unit(DistanceUnit.METERS);
    gdsb.order(SortOrder.ASC);
    gdsb.geoDistance(GeoDistance.PLANE);
    ssb.sort(gdsb);
    searchRequest.source(ssb);
    try {
      SearchResponse searchResponse =
          restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
      logger.info("searchResponse:{}", searchResponse);
      SearchHits hits = searchResponse.getHits();
      SearchHit[] hits2 = hits.getHits();
      for (SearchHit searchHit : hits2) {
        logger.info("hit:{}", searchHit);
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Test
  public void geoHashQueryTest() {
    GeoPoint srcPoint = new GeoPoint();
    srcPoint.reset(40.715D, -73.988D);
    // SearchRequest
    SearchRequest searchRequest = new SearchRequest("restaurant");
    // GeoDistanceQueryBuilder

    GeoDistanceQueryBuilder geoDistanceQuery = QueryBuilders.geoDistanceQuery("location");
    geoDistanceQuery.geoDistance(GeoDistance.PLANE);
    geoDistanceQuery.distance("2km", DistanceUnit.KILOMETERS);
    geoDistanceQuery.geohash(Geohash.stringEncode(srcPoint.getLon(), srcPoint.lat()));

    // SearchSourceBuilder
    SearchSourceBuilder ssb = new SearchSourceBuilder();
    ssb.query(geoDistanceQuery);
    // ssb.postFilter(geoDistanceQuery);
    // GeoDistanceSortBuilder
    GeoDistanceSortBuilder gdsb = new GeoDistanceSortBuilder("location", srcPoint);
    gdsb.unit(DistanceUnit.METERS);
    gdsb.order(SortOrder.ASC);
    gdsb.geoDistance(GeoDistance.PLANE);
    ssb.sort(gdsb);
    searchRequest.source(ssb);
    try {
      SearchResponse searchResponse =
          restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
      logger.info("searchResponse:{}", searchResponse);
      SearchHits hits = searchResponse.getHits();
      SearchHit[] hits2 = hits.getHits();
      for (SearchHit searchHit : hits2) {
        logger.info("hit:{}", searchHit);
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  private void logDistance(GeoPoint srcPoint, GeoPoint disPoint) {
    double calculate =
        GeoDistance.PLANE.calculate(
            srcPoint.getLat(),
            srcPoint.getLon(),
            disPoint.getLat(),
            disPoint.getLon(),
            DistanceUnit.METERS);
    logger.info("####distance:{}m", calculate);
  }
}
