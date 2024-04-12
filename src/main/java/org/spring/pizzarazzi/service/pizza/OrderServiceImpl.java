package org.spring.pizzarazzi.service.pizza;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.dto.pizza.DoughDTO;
import org.spring.pizzarazzi.dto.pizza.EdgeDTO;
import org.spring.pizzarazzi.dto.pizza.ToppingDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestPizzaOrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestTakeOrderDTO;
import org.spring.pizzarazzi.dto.response.order.ResponseGetOrderDTO;
import org.spring.pizzarazzi.dto.response.order.ResponseGetOrderListDTO;
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

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailToppingRepository orderDetailToppingRepository;

    @Override
    public KafkaOrderDTO orderPizza(Long memberId, RequestPizzaOrderDTO requestPizzaOrderDTO) {

        Long totalPrice = 0L;
        String pizzaName = "";

        DoughDTO doughDTO = doughService.findDoughById(requestPizzaOrderDTO.getDoughId());
        EdgeDTO edgeDTO = edgeService.findEdgeById(requestPizzaOrderDTO.getEdgeId());

        pizzaName = doughDTO.getName() + " " + edgeDTO.getName();

        List<ToppingDTO> toppings = new ArrayList<>();
        for (Long toppingId : requestPizzaOrderDTO.getToppings()) {
            ToppingDTO toppingDTO = toppingService.findToppingById(toppingId);
            toppings.add(toppingDTO);
            totalPrice += toppingDTO.getPrice();
            pizzaName += toppingDTO.getName() + " ";
        }
        totalPrice += doughDTO.getPrice() + edgeDTO.getPrice();

        //주문 상세
        OrderDetail orderDetail = OrderDetail.builder()
                .dough(doughDTO.toDough())
                .edge(edgeDTO.toEdge())
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
                .name(pizzaName)
                .totalPrice(totalPrice)
                .build();
        Order orderSave = orderRepository.save(order);


        return KafkaOrderDTOBuilderHelper.orderPizza(order.getMember().getEmail(), memberId)
                .orderId(orderSave.getId())
                .orderStatus(orderSave.getOrderStatus())
                .totalPrice(orderSave.getTotalPrice())
                .build();

    }

    @Override
    public KafkaOrderDTO takeOrder(RequestTakeOrderDTO requestTakeOrderDTO) {

        Order order = orderRepository.findById(requestTakeOrderDTO.getOrderId()).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
        order.setOrderStatus(OrderStatus.COOKING);

        return KafkaOrderDTOBuilderHelper.takeOrder(order.getMember().getId())
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    @Override
    public KafkaOrderDTO rejectOrder(RequestTakeOrderDTO requestTakeOrderDTO) {

        Order order = orderRepository.findById(requestTakeOrderDTO.getOrderId()).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
        order.setOrderStatus(OrderStatus.CANCELED);

        return KafkaOrderDTOBuilderHelper.rejectOrder(order.getMember().getId())
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .build();

    }

    @Override
    public KafkaOrderDTO cancelOrder(RequestTakeOrderDTO requestTakeOrderDTO) {

        Order order = orderRepository.findById(requestTakeOrderDTO.getOrderId()).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
        order.setOrderStatus(OrderStatus.CANCELED);

        return KafkaOrderDTOBuilderHelper.cancelOrder(order.getMember().getEmail(), order.getMember().getId())
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .build();

    }

    @Override
    public KafkaOrderDTO deliverOrder(RequestTakeOrderDTO requestTakeOrderDTO) {

        Order order = orderRepository.findById(requestTakeOrderDTO.getOrderId()).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
        order.setOrderStatus(OrderStatus.ON_DELIVERY);

        return KafkaOrderDTOBuilderHelper.deliverOrder(order.getMember().getId())
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .build();

    }

    @Override
    public KafkaOrderDTO completeOrder(RequestTakeOrderDTO requestTakeOrderDTO) {

        Order order = orderRepository.findById(requestTakeOrderDTO.getOrderId()).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
        order.setOrderStatus(OrderStatus.COMPLETED);

        return KafkaOrderDTOBuilderHelper.completeOrder(order.getMember().getEmail(), order.getMember().getId())
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .build();

    }

    @Override
    public List<ResponseGetOrderListDTO> findAllOrders(Long memberId) {
        return orderRepository.getAllByMemberId(memberId).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
    }

    @Override
    public ResponseGetOrderDTO findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
        OrderDetail byOrderId = orderDetailRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("주문 상세가 존재하지 않습니다."));
        List<OrderDetailTopping> byOrderDetailId = orderDetailToppingRepository.findByOrderDetailId(byOrderId.getId());

        List<String> toppings = new ArrayList<>();
        for (OrderDetailTopping orderDetailTopping : byOrderDetailId) {
            String toppingName = orderDetailTopping.getTopping().getName();
            toppings.add(toppingName);
        }


        return ResponseGetOrderDTO.builder()
                .orderId(order.getId())
                .orderDetailId(byOrderId.getId())
                .orderName(order.getName())
                .orderStatus(order.getOrderStatus())
                .orderTime(String.valueOf(order.getOrderTime()))
                .totalPrice(order.getTotalPrice())
                .dough(byOrderId.getDough().getName())
                .edge(byOrderId.getEdge().getName())
                .toppings(toppings)
                .build();

    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
        OrderDetail orderDetail = orderDetailRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("주문 상세가 존재하지 않습니다."));

        orderDetailToppingRepository.deleteByOrderDetailId(orderDetail.getId());
        orderDetailRepository.deleteByOrderId(orderId);
        orderRepository.delete(order);
    }

}
