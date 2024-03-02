package org.spring.pizzarazzi.repository.order;

import org.spring.pizzarazzi.model.order.Order;
import org.spring.pizzarazzi.model.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMemberId(Long memberId);

}
