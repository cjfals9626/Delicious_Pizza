package org.spring.pizzarazzi.util.kafka;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
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
    private List<KafkaOrderDTO> payloads = new ArrayList<>();
    private KafkaOrderDTO payload;

    // record 를 수신하기 위한 consumer 설정
    @KafkaListener(topics = "${kafka-config.topic}"
            /*, containerFactory = "filterListenerContainerFactory" KafkaConsumerConfig에서 필터를 설정했을 경우 활성화*/)
    public void receive(ConsumerRecord<String, KafkaOrderDTO> consumerRecord) {
        payload = consumerRecord.value();
        log.info("received payload = {}", payload.toString());
        payloads.add(payload);
        latch.countDown();
    }

    public List<KafkaOrderDTO> getPayloads() {
        return payloads;
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

}
