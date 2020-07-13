package com.yhy.controller;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("hello")
    public String test(@RequestParam String name){
        return "hello" + name;
    }


    @Autowired
    @Qualifier("LETTUCE_POOL")
    private GenericObjectPool<StatefulRedisConnection<String,String>> pool;

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



































