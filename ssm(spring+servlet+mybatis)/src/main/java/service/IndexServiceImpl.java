package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import helper.EsHelper;
import model.User;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;

import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private EsHelper help;

   //@Autowired
//   public IndexServiceImpl(EsHelper helper){
//       this.help = helper;
//   }

    /*插入一条数据*/
    @Override
    public void push(String id, User user)  {
       try {
            //region --1 使用json数据插入一条索引数据
           /*
           String json = "{" +
                   "\"user\":\"kimchy\"," +
                   "\"postDate\":\"2013-01-30\"," +
                   "\"message\":\"trying out Elasticsearch\"" +
                   "}";
           IndexResponse response = help.getConn().prepareIndex("twitter", "tweet")
                   .setSource(json, XContentType.JSON)
                   .get();*/
            //endregion

            //region --2 使用Map<String, Object>插入一条索引数据
           /*
           Map<String, Object> jsonMap = new HashMap<String, Object>();
           jsonMap.put("name", "jim" + id);
           jsonMap.put("age", 20 + id);
           jsonMap.put("date", new Date());
           jsonMap.put("message", "测试" + id);
           jsonMap.put("tel", "1234567");
           IndexResponse indexResponse = help.getConn().prepareIndex("mail", "mail", id)
                   .setSource(jsonMap)
                   .get();
           String _index = indexResponse.getIndex();
           String _type = indexResponse.getType();
           String _id = indexResponse.getId();
           long _version = indexResponse.getVersion();
           RestStatus status = indexResponse.status();
           System.out.println(_index + "_" + _type + "_" + _id + "_" + _version + "_" + status);*/
            //endregion

            //region --3 ObjectMapper序列化对象的方式插入一条索引数据（最终也是转成了json）
            /**
             * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
             * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
             * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
             * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
             * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
             * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
             */
               /**
                * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
                */
               ObjectMapper mapper = new ObjectMapper();
               String json = mapper.writeValueAsString(user);
               IndexResponse response = help.getConn().prepareIndex("user", "user")
                       .setSource(json, XContentType.JSON)
                       .get();
               RestStatus status = response.status();
               System.out.println(status);
            //endregion

           //region --4 使用Elasticsearch helper插入数据

           //endregion

       }catch (Exception e){ System.out.println( e.getMessage() )  ;}
    }

    /*
    * 批量导入数据
    * */
    @Override
    public void bulkProcess(String index, List<User> users) {
        try {
        //创建BulkProcessor对象
        BulkProcessor bulkProcessor = BulkProcessor.builder(help.getConn(),
            //region   Listener 匿名对象
            new BulkProcessor.Listener() {
            //bulk执行前，例如你可以看到numberOfActions要执行插入的数量： request.numberOfActions()
            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {
                System.out.println("numberOfActions:"+bulkRequest.numberOfActions());
            }
            //bulk执行后
            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                System.out.println("错误:"+bulkResponse.hasFailures());
            }
            //bulk报异常后
            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {

            }  /* Listener methods */
            })
            //endregion
            //region bulk设置
            .setBulkActions(10000)  //10000条执行一次
            .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB)) //每5m执行一次flush
            .setFlushInterval(TimeValue.timeValueSeconds(5)) //无论请求量多少，每5分钟执行一次flush
            .setConcurrentRequests(0) //设置并发请求的数量。值为0意味着只有一个请求将被允许执行。值为1表示在累积新的批量请求时允许执行1个并发请求。
            /*
            *
                设置一个自定义退避策略，最初等待100ms，成倍增长，重试三次。
                每当有一个或多个批量项目请求失败时，就会尝试重试，EsRejectedExecutionException
                这表示计算资源可用于处理请求的计算资源太少。要禁用退避，请传递BackoffPolicy.noBackoff()。
            * */
            .setBackoffPolicy(
                    BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
            .build();
            //endregion
        //region 数据导入
        for (User u :users){
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("id", u.getUser_id());
            jsonMap.put("name", u.getUser_name());
            jsonMap.put("date", new Date());
            jsonMap.put("address", u.getUser_address());
            jsonMap.put("tel", u.getUser_telephone());
            // Add your requests
            bulkProcessor.add(new IndexRequest(index, index).source(jsonMap));
            //bulkProcessor.add(new DeleteRequest("twitter", "tweet", "2"));
        }
            //bulkProcessor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        // Or close the bulkProcessor if you don't need it anymore
        bulkProcessor.close();
        //或
        bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
        // Refresh your indices
        client.admin().indices().prepareRefresh().get();
        //Now you can start searching!
        client.prepareSearch().get();*/
    }


//    https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-index.html#java-docs-index-generate-helpers

}
