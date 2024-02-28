package org.spring.pizzarazzi.kafka;

import org.junit.jupiter.api.Test;
import org.spring.pizzarazzi.dto.kafka.OrderDTO;
import org.spring.pizzarazzi.enums.OrderStatus;
import org.spring.pizzarazzi.util.kafka.KafkaConsumer;
import org.spring.pizzarazzi.util.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1,
        brokerProperties = {"listeners=PLAINTEXT://localhost:9092"},
        ports = { 9092 }
)
class KafkaConsumerTest {

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

    @Value("${kafka-config.topic}")
    private String topic;

    @Test
    public void giveEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
            throws Exception {



        OrderDTO payload = OrderDTO.builder()
                .memberId(1L)
                .orderId(1L)
                .orderStatus(OrderStatus.WATING)
                .totalPrice(1000L)
                .build();
        OrderDTO payload2 = OrderDTO.builder()
                .memberId(2L)
                .orderId(2L)
                .orderStatus(OrderStatus.WATING)
                .totalPrice(2000L)
                .build();

        int testCnt = 0;
        for (int i = 0; i < 10; i++) {
            if (testCnt % 2 == 0) {
                producer.send(topic, payload);
            } else {
                producer.send(topic, payload2);
            }
            testCnt++;
        };

        // 모든 메시지를 수신할 때까지 기다립니다.
        consumer.getLatch().await(10, TimeUnit.SECONDS);

        System.out.println("============================================================");
        System.out.println(consumer.getPayloads().size());
        System.out.println(consumer.getPayloads());


    }

}