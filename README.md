# deathknight

###  Elasticsearch(7.9.2)&Java High Level REST Client

 - 基本crud API (IndexRequest,GetRequest,DeleteRequest,UpdateRequest) demo
 - 批量bulk API (BulkRequest,MultiGetRequest,UpdateByQueryRequest,DeleteByQueryRequest) demo
 - 查询search API (SearchRequest,UpdateByQueryRequest) demo
 - 动态映射（dynamic mapping）地理坐标geo_point
 - GEO API distance&sort demo
 - GEO工具方法（静态方法）demo(距离计算&Geohash编码)EsGeoUtilTest.class
 - GEO距离计算分类：GeoDistance.PLANE（速度快精度低）  GeoDistance.ARC(API默认 速度慢精度高)
 - GEOHASH API 
 
 ----
 
###  Jackson demo


从fastjson迁移到Jackson上，编写JsonUtil工具类

- 设置Jackson TimeZone 为JVM默认时区
- 封装静态方法：toJsonString(Object obj) 
- 封装静态方法：<T> T json2Bean(String jsonStr, Class<T> beanType)
- 封装静态方法：<T> T json2GenericType(String jsonStr, Supplier<TypeReference<T>> suppiler)
- 格式化：@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
- 序列化属性名：@JsonProperty(value = "phoneNo")
- 序列化属性控制：序列化为读，反序列化为写 @JsonProperty(access = Access.READ_ONLY) 
- 序列序列化过滤：@JsonIgnore
- 反序列化，需要无参构造器
