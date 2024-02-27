package org.spring.pizzarazzi.repository.order;

import org.spring.pizzarazzi.model.order.Order;
import org.spring.pizzarazzi.model.order.OrderDetailTopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailToppingRepository extends JpaRepository<OrderDetailTopping, Long> {
}
