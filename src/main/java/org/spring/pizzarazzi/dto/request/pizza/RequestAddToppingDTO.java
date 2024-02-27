package org.spring.pizzarazzi.dto.request.pizza;

import lombok.*;
import org.spring.pizzarazzi.model.pizza.Topping;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestAddToppingDTO {
    private String name;
    private Integer price;

    public Topping toTopping() {
        return Topping.builder()
                .name(name)
                .price(price)
                .build();
    }
}
