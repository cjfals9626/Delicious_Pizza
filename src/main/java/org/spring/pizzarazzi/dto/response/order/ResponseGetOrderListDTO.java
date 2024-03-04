package org.spring.pizzarazzi.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.pizzarazzi.enums.OrderStatus;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseGetOrderListDTO {
    private Long orderId;
    private OrderStatus orderStatus;
    private Long totalPrice;
    private String orderName;
    private LocalDateTime orderTime;
}
