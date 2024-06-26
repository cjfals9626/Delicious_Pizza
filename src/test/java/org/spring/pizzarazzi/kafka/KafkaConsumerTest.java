package org.spring.pizzarazzi.kafka;

import org.junit.jupiter.api.Test;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.enums.OrderStatus;
import org.spring.pizzarazzi.util.kafka.KafkaConsumer;
import org.spring.pizzarazzi.util.kafka.KafkaOrderDTOBuilderHelper;
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
        brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "auto.create.topics.enable=true"},
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



        KafkaOrderDTO payload = KafkaOrderDTOBuilderHelper.toAdmin(1L)
                .orderId(1L)
                .orderStatus(OrderStatus.WAITING)
                .totalPrice(1000L)
                .build();
        KafkaOrderDTO payload2 = KafkaOrderDTOBuilderHelper.toAdmin(2L)
                .orderId(2L)
                .orderStatus(OrderStatus.WAITING)
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