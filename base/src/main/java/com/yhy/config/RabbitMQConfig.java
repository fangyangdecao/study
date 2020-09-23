package com.yhy.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("192.168.146.135");
        cachingConnectionFactory.setUsername("root");
        cachingConnectionFactory.setPassword("123456");
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate getSimpleRabbitMQ(){
        return new RabbitTemplate(connectionFactory());
    }
}
