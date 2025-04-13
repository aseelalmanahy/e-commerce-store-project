package com.example.productcatalog.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    @KafkaListener(topics = "order-topic", groupId = "product-service-consumer")
    public void handleOrderEvent(String message){
        System.out.println("Product Service received order event: " + message);
    }
}
