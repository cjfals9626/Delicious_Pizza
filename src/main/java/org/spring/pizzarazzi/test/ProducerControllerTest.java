package org.spring.pizzarazzi.test;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.kafka.OrderDTO;
import org.spring.pizzarazzi.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class ProducerControllerTest {
    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;

    @Value("${kafka-config.topic}")
    private String topicName;

    @GetMapping("/send")
    public String send() {
        OrderDTO orderDTO = OrderDTO.builder()
                .orderId(1L)
                .memberId(1L)
                .orderStatus(OrderStatus.WATING)
                .totalPrice(1000L)
                .build();
        kafkaTemplate.send(topicName, orderDTO);
        return "success";
    }

}
