package org.spring.pizzarazzi.dto.kafka;

import lombok.*;
import org.spring.pizzarazzi.enums.OrderStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaOrderDTO {
    private String from;
    private String to;
    private Long orderId;
    private OrderStatus orderStatus;
    private Long totalPrice;
    private String massage;
}
