package com.shuailee.springbootdemo;


import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisClusterTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    protected static final String GIFYBAG_LIST = "{giftbag}GrabList";
    protected static final String GIFYBAG_CONSUMED_LIST = "giftbagConsumedList_";
    protected static final String GIFYBAG_CONSUMED_MAP = "{giftbag}_ConsumedMap";
    protected static final String GIFYBAG_LOCK = "giftbag_lock";
    protected static final int GIFYBAG_LOCK_TIMEOUT = 5000;
    protected static final String cardno = "card11111";





    protected static final String  luademo="local current = redis.call('GET', KEYS[1])\n" +
            "if current ~= ARGV[1]\n" +
            "  then redis.call('SET', KEYS[1], ARGV[2])\n" +
            "  return true\n" +
            "end\n" +
            "return false";

    @Test
    void luademoTest() {
        List<String> keys=Arrays.asList("name");
        Boolean resu=  redisTemplate.execute(new DefaultRedisScript<Boolean>(luademo, Boolean.class),keys,"shuai","shuai2");
        System.out.println(resu);
    }



    @Test
    void name() {
        List<String> skulist = new ArrayList<String>() {{
            add(JSON.toJSONString(new Hongbao("a", "")));
            add(JSON.toJSONString(new Hongbao("B", "")));
            add(JSON.toJSONString(new Hongbao("c", "")));
            add(JSON.toJSONString(new Hongbao("d", "")));
        }};
        Collections.shuffle(skulist);

//         红包队列   GIFYBAG_LIST + 主卡no
        if (!redisTemplate.hasKey(GIFYBAG_LIST + cardno)) {
            redisTemplate.opsForList().leftPushAll(GIFYBAG_LIST + cardno, skulist);
        }
    }

//    @Test
//    public void lua() {
//
//        String sss="local gift = redis.call('rpop', KEYS[1]);  \n" +
//                "  if gift then  \n" +
//                "    local x = cjson.decode(gift);  \n" +
//                "    -- 加入用户ID信息  \n" +
//                "    x['userid'] = KEYS[3];  \n" +
//                "    local re = cjson.encode(x);     \n" +
//                "    return re;  \n" +
//                "  end \n" +
//                "return nil ";
//
//        List<String> keys = new ArrayList<>();
//        keys.add(GIFYBAG_LIST + cardno);
//        keys.add(GIFYBAG_CONSUMED_MAP + cardno);
//        keys.add("uid" + 1);
//        Hongbao hongbao = (Hongbao) redisTemplate.execute(new DefaultRedisScript<>(sss, Object.class), keys);
//        System.out.println(hongbao.getUserid() + "-" + hongbao.getSku());
//    }


    @Test
    public void getValue() {


        List<String> skulist = new ArrayList<String>() {{
            add(JSON.toJSONString(new Hongbao("a", "")));
            add(JSON.toJSONString(new Hongbao("B", "")));
            add(JSON.toJSONString(new Hongbao("c", "")));
            add(JSON.toJSONString(new Hongbao("d", "")));
        }};
        Collections.shuffle(skulist);

        //String cardno="card11111";
//         红包队列   GIFYBAG_LIST + 主卡no
        if (!redisTemplate.hasKey(GIFYBAG_LIST + cardno)) {
            redisTemplate.opsForList().leftPushAll(GIFYBAG_LIST + cardno, skulist);
        }
        for (int i = 0; i < 10; i++) {
            String uid = "uid" + i;

            String hongbao = redisTemplate.opsForList().leftPop(GIFYBAG_LIST + cardno);
            System.out.println("抢到了红包" + hongbao);

            if (StringUtils.isNotBlank(hongbao)) {
                //将抢到的红包放到消费集合
                Hongbao bao = JSON.parseObject(hongbao, Hongbao.class);
                bao.setUserid(uid);
                // redisTemplate.opsForList().leftPush(GIFYBAG_CONSUMED_LIST,JSON.toJSONString(bao));
                redisTemplate.opsForHash().put(GIFYBAG_CONSUMED_MAP + cardno, uid, JSON.toJSONString(bao));
                // 设置过期时间
                redisTemplate.expire(GIFYBAG_CONSUMED_MAP + cardno, 5, TimeUnit.MINUTES);
            }
        }

        System.out.println(redisTemplate.opsForHash().keys(GIFYBAG_CONSUMED_MAP + cardno));
        for (int i = 0; i < 10; i++) {
            String uid = "uid" + i;
            System.out.println("抢到的人：" + uid + " -" + redisTemplate.opsForHash().hasKey(GIFYBAG_CONSUMED_MAP + cardno, uid));
        }


//        ValueOperations<String,String> operations=redisTemplate.opsForValue();
//        operations.set("key1","kkk");
//        System.out.println(operations.get("key1"));


    }


    static class Hongbao implements Serializable {
        public Hongbao() {

        }

        public Hongbao(String sku, String userid) {
            this.sku = sku;
            this.userid = userid;
        }

        private String sku;
        private String userid;

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}


