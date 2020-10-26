# deathknight

###  Elasticsearch  Java High Level REST Client 

 - crud API(IndexRequest,GetRequest,DeleteRequest,UpdateRequest) demo
 - bulk API(BulkRequest,MultiGetRequest,UpdateByQueryRequest,DeleteByQueryRequest) demo
 - search API(SearchRequest) demo
 
 ----
 
###  Jackson demo


从fastjson迁移到Jackson上，编写JsonUtil工具类

- 设置Jackson TimeZone 为JVM默认时区
- 封装静态方法：toJsonString(Object obj) 
- 封装静态方法：<T> T json2Bean(String jsonStr, Class<T> beanType)
- 封装静态方法：<T> T json2GenericType(String jsonStr, Supplier<TypeReference<T>> suppiler)
- 格式化：@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
- 序列化属性名：@JsonProperty(value = "phoneNo")
