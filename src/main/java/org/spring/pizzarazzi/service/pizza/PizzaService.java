package org.spring.pizzarazzi.service.pizza;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.request.pizza.RequestPizzaOrderDTO;
import org.spring.pizzarazzi.model.order.Order;
import org.spring.pizzarazzi.model.order.OrderDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PizzaService {
    private final ToppingService toppingService;
    private final DoughService doughService;
    private final EdgeService edgeService;

    public void orderPizza(RequestPizzaOrderDTO requestPizzaOrderDTO) {
        Order order = requestPizzaOrderDTO.toOrder();
        OrderDetail orderDetail = requestPizzaOrderDTO.toOrderDetail();
    }
}
