package org.spring.pizzarazzi.model.order;

import jakarta.persistence.*;
import lombok.*;
import org.spring.pizzarazzi.model.pizza.Edge;
import org.spring.pizzarazzi.model.pizza.Topping;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailTopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_topping_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id")
    @ToString.Exclude
    private OrderDetail orderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topping_id")
    @ToString.Exclude
    private Topping topping;

}
