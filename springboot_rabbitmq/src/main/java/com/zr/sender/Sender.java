package com.zr.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消息发送方
 */
@Component
public class Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(){
        String message = "message" + new Date();
        System.out.println("Sender"+message);
        rabbitTemplate.convertAndSend("immediate_exchange_test1", "immediate_routing_key_test1", message);
    }
}
