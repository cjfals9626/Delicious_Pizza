package org.spring.pizzarazzi.test;

import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceTest {

    @KafkaListener(topics = "${kafka-config.topic}")
    public void receive(KafkaOrderDTO message) {
        System.out.println("=============================================================");
        System.out.println(message.toString());
    }
}
