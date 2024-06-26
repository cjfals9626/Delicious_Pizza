package org.spring.pizzarazzi.model.order;

import jakarta.persistence.*;
import lombok.*;
import org.spring.pizzarazzi.enums.OrderStatus;
import org.spring.pizzarazzi.model.pizza.Dough;
import org.spring.pizzarazzi.model.pizza.Edge;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "orderDetail", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dough_id")
    @ToString.Exclude
    private Dough dough;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edge_id")
    @ToString.Exclude
    private Edge edge;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderDetail")
    private List<OrderDetailTopping> orderDetailToppings = new ArrayList<>();


}
