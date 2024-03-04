package org.spring.pizzarazzi.repository.order;

import org.spring.pizzarazzi.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMemberId(Long memberId);

/*    @Query("select new org.spring.pizzarazzi.dto.response.order.ResponseGetOrdersDTO(o.id, od.id, ) " +
            "from Order o join fetch OrderDetail od on o.id=od.order.id " +
            "join fetch OrderDetailTopping odt on od.id=odt.orderDetail.id " +
            "where o.id=:id")
    Optional<ResponseGetOrdersDTO> getOrderById(@Param("id") Long orderId);*/

    Optional<Order> findById(Long orderId);

}
