package com.zr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消费消息
 */
@Component
public class KafkaMessageListener {


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @KafkaListener注解来监听名称为test的Topic，消费者分组的组名为test-consumer。
     * @param message
     */
    @KafkaListener(topics = "test",groupId = "test-consumer")
    public void listens(String message){
        logger.info("接收消息: {}", message);
    }
}
