package org.spring.pizzarazzi.service.pizza;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.dto.request.member.RequestMemberSignUpDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestPizzaOrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestTakeOrderDTO;
import org.spring.pizzarazzi.dto.response.order.ResponseGetOrdersDTO;
import org.spring.pizzarazzi.enums.OrderStatus;
import org.spring.pizzarazzi.enums.RoleType;
import org.spring.pizzarazzi.model.order.Order;
import org.spring.pizzarazzi.model.order.OrderDetail;
import org.spring.pizzarazzi.model.pizza.Dough;
import org.spring.pizzarazzi.model.pizza.Edge;
import org.spring.pizzarazzi.model.user.Member;
import org.spring.pizzarazzi.repository.order.OrderRepository;
import org.spring.pizzarazzi.service.member.MemberService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;


    private RequestTakeOrderDTO requestTakeOrderDTO;
    private Order order;
    @BeforeEach
    void initData(){
        requestTakeOrderDTO = RequestTakeOrderDTO.builder()
                .orderId(1L)
                .memberId(1L)
                .build();

        order = Order.builder()
                .id(1L)
                .orderDetail(OrderDetail.builder()
                        .id(1L)
                        .dough(Dough.builder().id(1L).build())
                        .edge(Edge.builder().id(1L).build())
                        .totalPrice(10000L)
                        .build())
                .member(Member.builder()
                        .id(1L)
                        .build())
                .orderTime(LocalDateTime.now())
                .orderStatus(OrderStatus.WATING)
                .build();

        when(orderRepository.findById(requestTakeOrderDTO.getOrderId())).thenReturn(Optional.ofNullable(order));
        when(orderRepository.save(any())).thenReturn(order);

    }

    @Test
    void takeOrder() {

        // given


        // when
        KafkaOrderDTO kafkaOrderDTO = orderService.takeOrder(requestTakeOrderDTO);

        // then
        Assertions.assertThat(kafkaOrderDTO.getOrderStatus()).isEqualTo(OrderStatus.COOKING);


    }

    @Test
    void rejectOrder() {

        // given

        // when
        KafkaOrderDTO orderDTO = orderService.rejectOrder(requestTakeOrderDTO);

        // then
        Assertions.assertThat(orderDTO.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    void cancelOrder() {

        // given

        // when
        KafkaOrderDTO orderDTO = orderService.cancelOrder(requestTakeOrderDTO);

        // then
        Assertions.assertThat(orderDTO.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    void deliverOrder() {
        // given

        // when
        KafkaOrderDTO orderDTO = orderService.deliverOrder(requestTakeOrderDTO);

        // then
        Assertions.assertThat(orderDTO.getOrderStatus()).isEqualTo(OrderStatus.ON_DELIVERY);
    }

    @Test
    void completeOrder() {
        // given

        // when
        KafkaOrderDTO orderDTO = orderService.completeOrder(requestTakeOrderDTO);

        // then
        Assertions.assertThat(orderDTO.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    void findOrderById(){
        // given
        Long orderId = 1L;

        // when
        ResponseGetOrdersDTO responseGetOrdersDTO = orderService.findOrderById(orderId);

        // then
        Assertions.assertThat(responseGetOrdersDTO.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }
}