package org.spring.pizzarazzi.service.pizza;

import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestPizzaOrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    KafkaOrderDTO orderPizza(Long memberId, RequestPizzaOrderDTO requestPizzaOrderDTO);
}
