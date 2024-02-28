package org.spring.pizzarazzi.util.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.pizzarazzi.dto.kafka.OrderDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;

    public void send(String topic, OrderDTO payload) {
        log.info("sending payloa={} to topic={}", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}
