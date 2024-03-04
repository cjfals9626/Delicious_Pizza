package org.spring.pizzarazzi.service.pizza;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestPizzaOrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestTakeOrderDTO;
import org.spring.pizzarazzi.enums.OrderStatus;
import org.spring.pizzarazzi.repository.order.OrderRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @Test
    void orderPizza() {
        // given
        Long memberId = 1L;
        RequestPizzaOrderDTO requestPizzaOrderDTO = RequestPizzaOrderDTO.builder()
                                                        .doughId(1L)
                                                        .edgeId(1L)
                                                        .toppings(
                                                                new ArrayList<Long>() {{
                                                                    add(1L);
                                                                    add(2L);
                                                                }}
                                                        )
                                                        .build();

        given(orderService.orderPizza(memberId, requestPizzaOrderDTO)).willReturn(KafkaOrderDTO.builder()
                        .to("ADMIN")
                        .from(String.valueOf(memberId))
                        .orderId(1L)
                        .orderStatus(OrderStatus.WATING)
                        .totalPrice(10000L)
                .build());

        // when
        KafkaOrderDTO orderDTO = orderService.orderPizza(memberId, requestPizzaOrderDTO);

        // then
        Assertions.assertThat(orderDTO.getOrderStatus()).isEqualTo(OrderStatus.WATING);

    }

    @Test
    void takeOrder() {

        // given
        RequestTakeOrderDTO requestTakeOrderDTO = RequestTakeOrderDTO.builder()
                .orderId(1L)
                .memberId(1L)
                .build();
        when(orderService.takeOrder(requestTakeOrderDTO)).thenReturn(KafkaOrderDTO.builder()
                .to(String.valueOf(requestTakeOrderDTO.getMemberId()))
                .from("ADMIN")
                .orderId(requestTakeOrderDTO.getOrderId())
                .orderStatus(OrderStatus.COOKING)
                .totalPrice(10000L)
                .build());

        // when
        KafkaOrderDTO orderDTO = orderService.takeOrder(requestTakeOrderDTO);

        // then
        Assertions.assertThat(orderDTO.getOrderStatus()).isEqualTo(OrderStatus.COOKING);


    }

    @Test
    void rejectOrder() {

        // given
        RequestTakeOrderDTO requestTakeOrderDTO = RequestTakeOrderDTO.builder()
                .orderId(1L)
                .memberId(1L)
                .build();
        when(orderService.rejectOrder(requestTakeOrderDTO)).thenReturn(KafkaOrderDTO.builder()
                .to(String.valueOf(requestTakeOrderDTO.getMemberId()))
                .from("ADMIN")
                .orderId(requestTakeOrderDTO.getOrderId())
                .orderStatus(OrderStatus.CANCELED)
                .totalPrice(10000L)
                .build());

        // when
        KafkaOrderDTO orderDTO = orderService.rejectOrder(requestTakeOrderDTO);

        // then
        Assertions.assertThat(orderDTO.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    void cancelOrder() {

        // given
        RequestTakeOrderDTO requestTakeOrderDTO = RequestTakeOrderDTO.builder()
                .orderId(1L)
                .memberId(1L)
                .build();
        when(orderService.cancelOrder(requestTakeOrderDTO)).thenReturn(KafkaOrderDTO.builder()
                .to("ADMIN")
                .from(String.valueOf(requestTakeOrderDTO.getMemberId()))
                .orderId(requestTakeOrderDTO.getOrderId())
                .orderStatus(OrderStatus.CANCELED)
                .totalPrice(10000L)
                .build());

        // when
        KafkaOrderDTO orderDTO = orderService.cancelOrder(requestTakeOrderDTO);

        // then
        Assertions.assertThat(orderDTO.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    void deliverOrder() {
        // given
        RequestTakeOrderDTO requestTakeOrderDTO = RequestTakeOrderDTO.builder()
                .orderId(1L)
                .memberId(1L)
                .build();
        when(orderService.deliverOrder(requestTakeOrderDTO)).thenReturn(KafkaOrderDTO.builder()
                .to(String.valueOf(requestTakeOrderDTO.getMemberId()))
                .from("ADMIN")
                .orderId(requestTakeOrderDTO.getOrderId())
                .orderStatus(OrderStatus.ON_DELIVERY)
                .totalPrice(10000L)
                .build());

        // when
        KafkaOrderDTO orderDTO = orderService.deliverOrder(requestTakeOrderDTO);

        // then
        Assertions.assertThat(orderDTO.getOrderStatus()).isEqualTo(OrderStatus.ON_DELIVERY);
    }

    @Test
    void completeOrder() {
        // given
        RequestTakeOrderDTO requestTakeOrderDTO = RequestTakeOrderDTO.builder()
                .orderId(1L)
                .memberId(1L)
                .build();
        when(orderService.completeOrder(requestTakeOrderDTO)).thenReturn(KafkaOrderDTO.builder()
                .to(String.valueOf(requestTakeOrderDTO.getMemberId()))
                .from("ADMIN")
                .orderId(requestTakeOrderDTO.getOrderId())
                .orderStatus(OrderStatus.COMPLETED)
                .totalPrice(10000L)
                .build());

        // when
        KafkaOrderDTO orderDTO = orderService.completeOrder(requestTakeOrderDTO);

        // then
        Assertions.assertThat(orderDTO.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }
}