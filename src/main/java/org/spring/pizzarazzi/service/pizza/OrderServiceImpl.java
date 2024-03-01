package org.spring.pizzarazzi.service.pizza;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.dto.pizza.DoughDTO;
import org.spring.pizzarazzi.dto.pizza.EdgeDTO;
import org.spring.pizzarazzi.dto.pizza.ToppingDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestPizzaOrderDTO;
import org.spring.pizzarazzi.enums.OrderStatus;
import org.spring.pizzarazzi.model.order.Order;
import org.spring.pizzarazzi.model.order.OrderDetail;
import org.spring.pizzarazzi.model.order.OrderDetailTopping;
import org.spring.pizzarazzi.repository.order.OrderDetailRepository;
import org.spring.pizzarazzi.repository.order.OrderDetailToppingRepository;
import org.spring.pizzarazzi.repository.order.OrderRepository;
import org.spring.pizzarazzi.service.member.MemberService;
import org.spring.pizzarazzi.util.kafka.KafkaOrderDTOBuilderHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ToppingService toppingService;
    private final DoughService doughService;
    private final EdgeService edgeService;
    private final MemberService memberService;

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailToppingRepository orderDetailToppingRepository;

    public KafkaOrderDTO orderPizza(Long memberId, RequestPizzaOrderDTO requestPizzaOrderDTO) {

        Long totalPrice = 0L;

        DoughDTO doughDTO = doughService.findDoughById(requestPizzaOrderDTO.getDoughId());
        EdgeDTO edgeDTO = edgeService.findEdgeById(requestPizzaOrderDTO.getEdgeId());
        List<ToppingDTO> toppings = new ArrayList<>();
        for (Long toppingId : requestPizzaOrderDTO.getToppings()) {
            ToppingDTO toppingDTO = toppingService.findToppingById(toppingId);
            toppings.add(toppingDTO);
            totalPrice += toppingDTO.getPrice();
        }
        totalPrice += doughDTO.getPrice() + edgeDTO.getPrice();

        //주문 상세
        OrderDetail orderDetail = OrderDetail.builder()
                .dough(doughDTO.toDough())
                .edge(edgeDTO.toEdge())
                .totalPrice(totalPrice)
                .build();
        orderDetailRepository.save(orderDetail);


        //주문 상세 - 토핑 다대다 테이블
        List<OrderDetailTopping> orderDetailToppings = new ArrayList<>();
        for (ToppingDTO topping : toppings) {
            OrderDetailTopping orderDetailTopping = OrderDetailTopping.builder()
                    .orderDetail(orderDetail)
                    .topping(topping.toTopping())
                    .build();
            orderDetailToppings.add(orderDetailTopping);
        }
        orderDetailToppingRepository.saveAll(orderDetailToppings);

        //주문
        Order order = Order.builder()
                .member(memberService.getMember(memberId))
                .orderTime(LocalDateTime.now())
                .orderDetail(orderDetail)
                .orderStatus(OrderStatus.WATING)
                .build();
        Order orderSave = orderRepository.save(order);

        return KafkaOrderDTOBuilderHelper.toAdmin(memberId)
                .orderId(orderSave.getId())
                .orderStatus(orderSave.getOrderStatus())
                .totalPrice(orderSave.getOrderDetail().getTotalPrice())
                .build();

    }
}
