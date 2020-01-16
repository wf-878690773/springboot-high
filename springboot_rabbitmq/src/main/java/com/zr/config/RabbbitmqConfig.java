package com.zr.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbbitmqConfig {


    /**
     *
         创建一个立即消费队列
     */
    @Bean
    public Queue immediateQueue() {
        // 第一个参数是创建的queue的名字，第二个参数是是否支持持久化
        return new Queue("immediate_queue_test1", true);
    }


    /**
     * 创建一个立即消费信道
     * @return
     */
    @Bean
    public DirectExchange immediateExchange(){

        //一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除
        return new DirectExchange("immediate_exchange_test1", true, false);
    }

    @Bean

    /**
     * 把立即消费的队列和立即消费的exchange绑定在一起
     */
    public Binding immediateBinding() {

        return BindingBuilder.bind(immediateQueue()).to(immediateExchange()).with("immediate_routing_key_test1");
    }



}
