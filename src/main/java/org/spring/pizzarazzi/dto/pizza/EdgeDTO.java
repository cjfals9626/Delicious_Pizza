package org.spring.pizzarazzi.dto.pizza;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.pizzarazzi.model.pizza.Dough;
import org.spring.pizzarazzi.model.pizza.Edge;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EdgeDTO {
    private Long id;
    private String name;
    private Integer price;

    public Edge toEdge() {
        return Edge.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
    }

}
