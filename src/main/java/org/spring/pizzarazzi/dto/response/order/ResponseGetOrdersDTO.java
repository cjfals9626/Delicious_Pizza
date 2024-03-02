package org.spring.pizzarazzi.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGetOrdersDTO {
    private Long orderId;
    private String orderName;
    private String memberAddress;
    private String orderStatus;
    private String orderTime;
    private Long totalPrice;
    private String dough;
    private String edge;
    private List<String> toppings;
}
