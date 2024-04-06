package org.spring.pizzarazzi.repository.order;

import org.junit.jupiter.api.*;
import org.spring.pizzarazzi.model.order.OrderDetail;
import org.spring.pizzarazzi.model.order.OrderDetailTopping;
import org.spring.pizzarazzi.model.pizza.Dough;
import org.spring.pizzarazzi.model.pizza.Edge;
import org.spring.pizzarazzi.model.pizza.Topping;
import org.spring.pizzarazzi.repository.pizza.ToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    //  임베디드 데이터 베이스를 사용 안한다는 선언.
class OrderDetailToppingRepositoryTest {

    @Autowired
    private OrderDetailToppingRepository orderDetailToppingRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    private List<OrderDetailTopping> orderDetailToppings;
    private OrderDetail orderDetail;
    private List<Topping> toppings;

    @BeforeEach
    void setUp() {
        toppings = new ArrayList<>();
        orderDetailToppings = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Topping topping = Topping.builder()
                    .name("topping" + i)
                    .price(1000)
                    .build();
            toppings.add(topping);
        }
        orderDetail = OrderDetail.builder()
                .id(1L)
                .edge(Edge.builder()
                        .id(1L)
                        .name("edge")
                        .price(1000)
                        .build())
                .dough(Dough.builder()
                        .id(1L)
                        .name("dough")
                        .price(1000)
                        .build())
                .orderDetailToppings(orderDetailToppings)
                .build();
        orderDetailToppings.add(OrderDetailTopping.builder()
                .topping(toppings.get(0))
                .orderDetail(orderDetail)
                .build());

    }

    @Test
    void findByOrderDetailId() {
        //given
        toppingRepository.save(toppings.get(0));
        toppingRepository.save(toppings.get(1));
        orderDetailRepository.save(orderDetail);
        orderDetailToppingRepository.save(orderDetailToppings.get(0));

        //when
        List<OrderDetailTopping> result = orderDetailToppingRepository.findByOrderDetailId(1L);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void deleteByOrderDetailId() {
        //given
        toppingRepository.save(toppings.get(0));
        toppingRepository.save(toppings.get(1));
        orderDetailRepository.save(orderDetail);
        orderDetailToppingRepository.save(orderDetailToppings.get(0));

        //when
        orderDetailToppingRepository.deleteByOrderDetailId(1L);

        //then
        List<OrderDetailTopping> result = orderDetailToppingRepository.findByOrderDetailId(1L);
        assertThat(result).isEmpty();
    }
}