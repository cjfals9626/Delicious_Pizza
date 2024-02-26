package org.spring.pizzarazzi.dto.request.pizza;

import lombok.*;
import org.spring.pizzarazzi.enums.RoleType;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RequestPizzaOrderDTO {
    private Long doughId;
    private Long edgeId;
    private List<Long> toppings;
}
