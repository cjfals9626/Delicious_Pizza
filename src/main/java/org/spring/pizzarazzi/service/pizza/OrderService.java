package org.spring.pizzarazzi.service.pizza;

import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestPizzaOrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestTakeOrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    KafkaOrderDTO orderPizza(Long memberId, RequestPizzaOrderDTO requestPizzaOrderDTO);

    KafkaOrderDTO takeOrder(RequestTakeOrderDTO requestTakeOrderDTO);

    KafkaOrderDTO rejectOrder(RequestTakeOrderDTO requestTakeOrderDTO);

    KafkaOrderDTO cancelOrder(RequestTakeOrderDTO requestTakeOrderDTO);

    KafkaOrderDTO deliverOrder(RequestTakeOrderDTO requestTakeOrderDTO);

    KafkaOrderDTO completeOrder(RequestTakeOrderDTO requestTakeOrderDTO);

    Object findAllOrders(Long memberId);

    Object findOrderById(Long orderId);
}
