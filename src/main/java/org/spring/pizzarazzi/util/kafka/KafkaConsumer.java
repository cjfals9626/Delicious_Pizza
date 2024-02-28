package org.spring.pizzarazzi.util.kafka;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.spring.pizzarazzi.dto.kafka.OrderDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
@Data
public class KafkaConsumer {

    private CountDownLatch latch = new CountDownLatch(10);
    private List<OrderDTO> payloads = new ArrayList<>();
    private OrderDTO payload;

    // record 를 수신하기 위한 consumer 설정
    @KafkaListener(topics = "kafkaTest"
            /*, containerFactory = "filterListenerContainerFactory"*/)
    public void receive(ConsumerRecord<String, OrderDTO> consumerRecord) {
        payload = consumerRecord.value();
        log.info("received payload = {}", payload.toString());
        payloads.add(payload);
        latch.countDown();
    }

    public List<OrderDTO> getPayloads() {
        return payloads;
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

}
