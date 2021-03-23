# deathknight

### 支付流程(支付/微信)

- IJPay
- 支付宝支付（pc网页）
- 微信支付
- QrCode
- 支付结构
- 订单关闭延迟队列
- 支付渠道(支付/微信)切换
- 差集对账
- 支付流水号生成

https://github.com/Javen205/IJPay

  ----
### MDC 
 - 线程池MDC配置 MdcTaskDecorator
  ----
### Sign
 - 微信H5签名方法(MD5)
https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=4_3

- JSON/JSONArray to MAP
 ----
### Retry
- Spring Retry
- resilience4j-retry
 ----
### Spring EL
- Spring EL 表达式（SpelAspec）

 ----

### Redisson(3.14.1)
 - 延迟队列（DelayedQueueService）demo
 - 分布式任务调度(RedissionCfg,SchedulerServer)demo

 ----

###  Elasticsearch(7.9.2)&Java High Level REST Client

 - 基本crud API (IndexRequest,GetRequest,DeleteRequest,UpdateRequest) demo
 - 批量bulk API (BulkRequest,MultiGetRequest,UpdateByQueryRequest,DeleteByQueryRequest) demo
 - 查询search API (SearchRequest,UpdateByQueryRequest,SearchScrollRequest) demo
 - 动态映射（dynamic mapping）地理坐标geo_point
 - 脚本示例 Script painless更新文档
 - GEO API distance&sort demo
 - GEO工具方法（静态方法）demo(距离计算&Geohash编码)EsGeoUtilTest.class
 - GEO距离计算分类：GeoDistance.PLANE（速度快精度低）  GeoDistance.ARC(API默认 速度慢精度高)
 - GEOHASH概念理解 (The geohash_cell query has been removed(7.5) )
 - 封装RestHighLevelClientHandler工具类&自定义EsFunction统一处理IOException
 
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

 ----
