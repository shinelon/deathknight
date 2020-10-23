package com.shinelon.deathknight.utils;

import java.time.ZoneId;
import java.util.TimeZone;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * *
 *
 * @author syq
 */
public class JsonUtil {

  private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
  public static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
  }
  /**
   * * bean -> jsonStrs
   *
   * @param obj
   * @return
   */
  public static String toJsonString(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }
  /**
   * * jsonStr -> bean
   *
   * @param <T>
   * @param jsonStr
   * @param beanType
   * @return
   */
  public static <T> T json2Bean(String jsonStr, Class<T> beanType) {
    try {
      T result = objectMapper.readValue(jsonStr, beanType);
      return result;
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }
  /**
   * * jsonStr -> collection Or map
   *
   * <p>suppiler: () -> new TypeReference<Map<String,Object>>(){} () -> new
   * TypeReference<List<Object>>(){}
   *
   * @param <T>
   * @param jsonStr
   * @param suppiler
   * @return
   */
  public static <T> T json2GenericType(String jsonStr, Supplier<TypeReference<T>> suppiler) {
    try {
      T result = objectMapper.readValue(jsonStr, suppiler.get());
      return result;
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }
}
