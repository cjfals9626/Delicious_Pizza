package org.spring.pizzarazzi.repository.order;

import org.spring.pizzarazzi.model.order.OrderDetail;
import org.spring.pizzarazzi.model.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Long> {

    Optional<OrderDetail> findByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);
}
