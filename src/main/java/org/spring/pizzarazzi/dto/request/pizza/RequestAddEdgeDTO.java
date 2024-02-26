package org.spring.pizzarazzi.dto.request.pizza;

import lombok.*;
import org.spring.pizzarazzi.model.pizza.Edge;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestAddEdgeDTO {
    private String name;
    private Integer price;

    public Edge toEdge() {
        return Edge.builder()
                .name(name)
                .price(price)
                .build();
    }
}
