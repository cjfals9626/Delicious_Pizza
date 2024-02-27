package org.spring.pizzarazzi.dto.request.pizza;

import lombok.*;
import org.spring.pizzarazzi.model.pizza.Dough;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RequestAddDoughDTO {
    private String name;
    private Integer price;

    public Dough toDough() {
        return Dough.builder()
                .name(name)
                .price(price)
                .build();
    }
}
