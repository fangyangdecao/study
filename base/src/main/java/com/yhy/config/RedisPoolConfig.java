package com.yhy.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

@Configuration
public class RedisPoolConfig {

    @Bean
    @Qualifier("LETTUCE_POOL")
    public GenericObjectPool<StatefulRedisConnection<String,String>> getLettucePool(){
        RedisClient redisClient = RedisClient.create("redis://192.168.146.135:6379/0");
        System.out.println("注册bean成功");

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setJmxEnabled(false);
        poolConfig.setMaxTotal(2);
        //最大存在两个连接。超过的等待
        poolConfig.setMaxIdle(2);
        //空闲时保持多少个连接
        poolConfig.setMinIdle(0);
        return ConnectionPoolSupport.createGenericObjectPool(redisClient::connect, poolConfig);
    }

    @Bean
    @Qualifier("JEDIS_POOL")
    public Pool<Jedis> getJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMinIdle(4);
        config.setTestWhileIdle(true);

        return new JedisPool(config,
                "192.168.146.135",
                6379,
                2000,
                null,
                0);
    }
}
