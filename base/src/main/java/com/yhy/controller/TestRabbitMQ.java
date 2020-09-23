package com.yhy.controller;


import com.yhy.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class TestRabbitMQ{
    public static void main(String[] args) {
        RabbitMQConfig rabbitMQConfig = new RabbitMQConfig();
        RabbitTemplate simpleRabbitMQ = rabbitMQConfig.getSimpleRabbitMQ();
        simpleRabbitMQ.convertAndSend("yhy","yhy*","test");
        Object o = simpleRabbitMQ.convertSendAndReceive("yhy", "yhy*", "test");
    }
}
