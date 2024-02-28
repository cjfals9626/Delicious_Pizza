package org.spring.pizzarazzi.controller;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.common.MsgDTO;
import org.spring.pizzarazzi.dto.kafka.OrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.*;
import org.spring.pizzarazzi.exception.DuplicateMemberException;
import org.spring.pizzarazzi.service.pizza.OrderService;
import org.spring.pizzarazzi.util.jwt.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.spring.pizzarazzi.util.prefix.ConstPrefix.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final TokenProvider tokenProvider;

    @PostMapping("/order")
    public ResponseEntity<MsgDTO> pizzaOrder(@RequestBody RequestPizzaOrderDTO requestPizzaOrderDTO, @RequestHeader(value = AUTHORIZATION) String accessToken) {
        Long memberId = Long.valueOf(tokenProvider.getId(tokenProvider.resolveToken(accessToken)));
        try{
            OrderDTO orderDTO = orderService.orderPizza(memberId, requestPizzaOrderDTO);
        }catch (DuplicateMemberException e) {
            return ResponseEntity.ok(new MsgDTO(false, "주문 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "주문 성공", null));
    }



}
