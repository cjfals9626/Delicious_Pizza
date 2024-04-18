package org.spring.pizzarazzi.service.pizza;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestTakeOrderDTO;
import org.spring.pizzarazzi.dto.response.order.ResponseGetOrderDTO;
import org.spring.pizzarazzi.dto.response.order.ResponseGetOrderListDTO;
import org.spring.pizzarazzi.enums.OrderStatus;
import org.spring.pizzarazzi.model.order.Order;
import org.spring.pizzarazzi.model.order.OrderDetail;
import org.spring.pizzarazzi.model.pizza.Dough;
import org.spring.pizzarazzi.model.pizza.Edge;
import org.spring.pizzarazzi.model.pizza.Topping;
import org.spring.pizzarazzi.model.user.Member;
import org.spring.pizzarazzi.repository.order.OrderDetailRepository;
import org.spring.pizzarazzi.repository.order.OrderDetailToppingRepository;
import org.spring.pizzarazzi.repository.order.OrderRepository;
import org.spring.pizzarazzi.service.member.MemberService;
import scala.Int;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderDetailToppingRepository orderDetailToppingRepository;


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
                        .build())
                .member(Member.builder()
                        .id(1L)
                        .build())
                .orderTime(LocalDateTime.now())
                .orderStatus(OrderStatus.WAITING)
                .name("피자")
                .totalPrice(10000L)
                .build();

        //lenient() : mock 객체의 메소드가 호출되지 않았을 때도 에러를 발생시키지 않는다.
        lenient().when(orderRepository.findById(requestTakeOrderDTO.getOrderId())).thenReturn(Optional.ofNullable(order));
        lenient().when(orderRepository.save(any())).thenReturn(order);


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
        when(orderDetailRepository.findByOrderId(orderId)).thenReturn(Optional.ofNullable(order.getOrderDetail()));
        when(orderDetailToppingRepository.findByOrderDetailId(orderId)).thenReturn(new ArrayList<>());

        // when
        ResponseGetOrderDTO responseGetOrderDTO = orderService.findOrderById(orderId);

        // then
        Assertions.assertThat(responseGetOrderDTO.getOrderStatus()).isEqualTo(OrderStatus.WAITING);
    }

    @Test
    void findAllOrders() {
        // given
        Long memberId = 1L;

        List<ResponseGetOrderListDTO> responseGetOrderListDTOS = new ArrayList<>();
        responseGetOrderListDTOS.add(ResponseGetOrderListDTO.builder()
                .orderId(1L)
                .orderStatus(OrderStatus.WAITING)
                .orderTime(LocalDateTime.now())
                .totalPrice(10000L)
                .build());

        responseGetOrderListDTOS.add(ResponseGetOrderListDTO.builder()
                .orderId(2L)
                .orderStatus(OrderStatus.WAITING)
                .orderTime(LocalDateTime.now())
                .totalPrice(20000L)
                .build());

        when(orderRepository.getAllByMemberId(memberId)).thenReturn(Optional.of(responseGetOrderListDTOS));

        // when
        List<ResponseGetOrderListDTO> allOrders = orderService.findAllOrders(memberId);

        // then
        Assertions.assertThat(allOrders.size()).isEqualTo(2);
    }
}