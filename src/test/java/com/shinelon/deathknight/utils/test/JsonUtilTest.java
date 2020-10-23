package com.shinelon.deathknight.utils.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shinelon.deathknight.bean.Post;
import com.shinelon.deathknight.bean.Tag;
import com.shinelon.deathknight.bean.User;
import com.shinelon.deathknight.utils.JsonUtil;

/**
 * *
 *
 * @author syq
 */
public class JsonUtilTest {
  private static final Logger logger = LoggerFactory.getLogger(JsonUtilTest.class);
  private User author;
  private List<Tag> tags;
  private Post post;

  @BeforeEach
  public void init() {
    author = new User();
    author.setName("author");
    author.setMobile("18600000000");

    tags = new ArrayList<>();
    tags.add(new Tag("JAVA"));
    tags.add(new Tag("C++"));

    post = new Post();
    post.setAuthor(author);
    post.setTags(tags);
    post.setTitle("title");
    post.setContent("content");
    post.setCreateDate(new Date());
  }

  @Test
  public void obj2JsonStrTest() {
    logger.info("______");
    logger.info(JsonUtil.toJsonString(author));
    logger.info(JsonUtil.toJsonString(tags));
    logger.info(JsonUtil.toJsonString(post));
  }

  @Test
  public void json2Bean() {
    String jsonStr =
        "[{\"name\":\"author\",\"mobile\":\"18600000000\"},{\"name\":\"author1\",\"mobile\":\"18600000001\"}]";
    List<User> list = JsonUtil.json2GenericType(jsonStr, () -> new TypeReference<List<User>>() {});
    logger.info("list:{}", list);
    List<Map<String, String>> map =
        JsonUtil.json2GenericType(jsonStr, () -> new TypeReference<List<Map<String, String>>>() {});
    logger.info("map:{}", map);
  }

  @Test
  public void json2beanWithDate() {
    String jsonStr =
        "{\"title\":\"title\",\"author\":{\"name\":\"author\",\"phoneNo\":\"18600000000\"},\"tags\":[{\"name\":\"JAVA\"},{\"name\":\"C++\"}],\"createDate\":1603360294453,\"content\":\"content\"}";
//	  String jsonStr =
//	        "{\"title\":\"title\",\"author\":{\"name\":\"author\",\"phoneNo\":\"18600000000\"},\"tags\":[{\"name\":\"JAVA\"},{\"name\":\"C++\"}],\"createDate\":\"2020-10-22 18:15:19\",\"content\":\"content\"}";
    Post post = JsonUtil.json2GenericType(jsonStr, () -> new TypeReference<Post>() {});
    		//JsonUtil.json2Bean(jsonStr, Post.class); 
    logger.info("post:{}", post);
  }
}
