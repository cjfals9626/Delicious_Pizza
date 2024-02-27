package org.spring.pizzarazzi.dto.pizza;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.pizzarazzi.model.pizza.Dough;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoughDTO {
    private Long id;
    private String name;
    private Integer price;

    public Dough toDough() {
        return Dough.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
    }
}
