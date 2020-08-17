package com.yhy.controller;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.internal.LettuceLists;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.util.Pool;
import sun.misc.GC;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("test")
public class TestController {
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("LETTUCE_POOL")
    private GenericObjectPool<StatefulRedisConnection<String,String>> pool;

    @Autowired
    @Qualifier("JEDIS_POOL")
    private Pool<Jedis> jedisPool;

    Executor executor = Executors.newFixedThreadPool(10);

    @GetMapping("test-adaptor")
    public String testAdaptor(HttpServletRequest request){
        System.out.println(Thread.currentThread().getName());
        try (Jedis resource = jedisPool.getResource()){
            //HashMap<String, String> map = Maps.newHashMap();
            //map.put("sub",String.valueOf(1));
            //resource.hmset("test",map);
            String s = resource.get("travel.reward.100");
            resource.set("travel:reward:100","1");
            //resource.set("travel.reward.300","1");
            //resource.set("travel.reward.600","1");
            //resource.set("travel.reward.1500","1");
            //resource.set("travel.reward.6666","1");
            //resource.set("travel.reward.leopard","1");
            //resource.set("travel.reward.wartermelon-man","1");
            System.out.println(s);
            String s1 = resource.get("travel.reward.300");
            System.out.println(s1);
            String s2 = resource.get("travel.reward.600");
            System.out.println(s2);
            String s3 = resource.get("travel.reward.1500");
            System.out.println(s3);
            String s4 = resource.get("travel.reward.6666");
            System.out.println(s4);
            String s5 = resource.get("travel.reward.wartermelon-man");
            System.out.println(s5);
            String s6 = resource.get("travel.reward.leopard");
            System.out.println(s6);
        }
        return "hello";
    }

    @GetMapping("hello")
    public String test(@RequestParam String name){
        //Object lettuce_pool = applicationContext.getBean("redisPoolConfig");
        try (Jedis jedis = jedisPool.getResource()) {
            List<Response<Long>> objects = LettuceLists.newList();
            Pipeline pp = jedis.pipelined();
            for (int i = 0; i < 10; i++) {
                Response<Long> key = pp.hset("key", "pp" + i, String.valueOf(i));
                objects.add(key);
            }
            pp.sync();
            for (Response<Long> object : objects) {
                System.out.println(object.get());
            }
        }
        return "hello" + name;
    }


    @GetMapping("redis")
    public void redis(@RequestParam String name) throws InterruptedException {
/*
        RedisClient redisClient = RedisClient.create("redis://192.168.146.135:6379/0");

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(4);
        poolConfig.setMaxIdle(2);
        poolConfig.setMinIdle(2);

        GenericObjectPool<StatefulRedisConnection<String,String>> pool = ConnectionPoolSupport.createGenericObjectPool(redisClient::connect, poolConfig);*/
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2000);
            int a= i;
            CompletableFuture.runAsync(
                    ()->{
                        try (StatefulRedisConnection<String, String> connection = pool.borrowObject()) {
                            System.out.println("获取到连接");
                            Thread.sleep(10000);
                            RedisCommands<String, String> sync = connection.sync();
                            System.out.println(sync.ttl("jack"));
                            System.out.println("第" +a+"次获取连接:" + connection.toString());
                            Thread.sleep(2000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
        }


/*      //直接连接
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> sync = connect.sync();
        sync.set(name,"hello");
        sync.setex(name+1,5,"expired");
        sync.ttl("test");
        connect.close();
        redisClient.shutdown();*/
    }
}



































