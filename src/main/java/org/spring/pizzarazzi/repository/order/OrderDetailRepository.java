package org.spring.pizzarazzi.repository.order;

import org.spring.pizzarazzi.model.order.OrderDetail;
import org.spring.pizzarazzi.model.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Long> {
}
