package org.spring.pizzarazzi.dto.pizza;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.pizzarazzi.model.pizza.Edge;
import org.spring.pizzarazzi.model.pizza.Topping;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToppingDTO {
    private Long id;
    private String name;
    private Integer price;

    public Topping toTopping() {
        return Topping.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
    }
}
