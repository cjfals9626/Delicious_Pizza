package org.spring.pizzarazzi.util.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, KafkaOrderDTO> kafkaTemplate;

    public void send(String topic, KafkaOrderDTO payload) {
        log.info("sending payloa={} to topic={}", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}
