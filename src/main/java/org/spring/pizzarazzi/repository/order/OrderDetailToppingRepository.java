package org.spring.pizzarazzi.repository.order;

import org.spring.pizzarazzi.model.order.OrderDetailTopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailToppingRepository extends JpaRepository<OrderDetailTopping, Long> {
    List<OrderDetailTopping> findByOrderDetailId(Long orderDetailId);

    void deleteByOrderDetailId(Long orderDetailId);
}
