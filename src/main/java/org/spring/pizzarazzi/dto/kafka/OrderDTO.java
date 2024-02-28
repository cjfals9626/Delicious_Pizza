package org.spring.pizzarazzi.dto.kafka;

import lombok.*;
import org.spring.pizzarazzi.enums.OrderStatus;
import org.spring.pizzarazzi.util.serialize.OrderDTODeserializer;
import org.spring.pizzarazzi.util.serialize.OrderDTOSerializer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long memberId;
    private Long orderId;
    private OrderStatus orderStatus;
    private Long totalPrice;
}
