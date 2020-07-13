package com.yhy.controller;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("hello")
    public String test(@RequestParam String name){
        return "hello" + name;
    }


    @GetMapping("redis")
    public void redis(@RequestParam String name){
        RedisClient redisClient = RedisClient.create("redis://192.168.146.135:6379/0");
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> sync = connect.sync();
        sync.set(name,"hello");
        sync.setex(name+1,5,"expired");
        sync.ttl("test");
        connect.close();
        redisClient.shutdown();
    }
}
