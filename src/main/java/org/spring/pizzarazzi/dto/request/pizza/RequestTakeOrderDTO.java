package org.spring.pizzarazzi.dto.request.pizza;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.pizzarazzi.enums.OrderStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestTakeOrderDTO {
    private Long orderId;
    private Long memberId;
}
