package org.spring.pizzarazzi.service.pizza;

import org.spring.pizzarazzi.dto.kafka.OrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestPizzaOrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    OrderDTO orderPizza(Long memberId, RequestPizzaOrderDTO requestPizzaOrderDTO);
}
