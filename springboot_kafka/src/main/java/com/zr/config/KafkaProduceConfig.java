package com.zr.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置生产者
 */
@Configuration
public class KafkaProduceConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    /**
     * Kafka Producer实例的策略
     * ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
     * 和ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
     * 指定了key，value序列化策略，这里指定为Kafka提供的StringSerializer，
     * 因为我们暂时只发送简单的String类型的消息。
     * @return
     */
    @Bean
    public ProducerFactory<String,String> producerFactory(){

        Map<String,Object> map = new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(map);
    }


}
