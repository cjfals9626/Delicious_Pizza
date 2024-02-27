package org.spring.pizzarazzi.dto.pizza;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToppingDTO {
    private Long id;
    private String name;
    private Integer price;
}
