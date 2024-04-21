package org.spring.pizzarazzi.dto.request.pizza;

import lombok.*;
import org.spring.pizzarazzi.enums.RoleType;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestPizzaOrderDTO {
    private Long doughId;
    private Long edgeId;
    private List<Long> toppings;
    private String address;
    private String zoneCode;
    private String detailAddress;
}
