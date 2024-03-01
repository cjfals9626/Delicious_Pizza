package org.spring.pizzarazzi.controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.spring.pizzarazzi.dto.common.MsgDTO;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.*;
import org.spring.pizzarazzi.exception.DuplicateMemberException;
import org.spring.pizzarazzi.service.pizza.OrderService;
import org.spring.pizzarazzi.util.jwt.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import static org.spring.pizzarazzi.util.prefix.ConstPrefix.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final TokenProvider tokenProvider;
    private final KafkaTemplate<String, KafkaOrderDTO> kafkaTemplate;



    @PostMapping("/order")
    public ResponseEntity<MsgDTO> pizzaOrder(@RequestBody RequestPizzaOrderDTO requestPizzaOrderDTO, @RequestHeader(value = AUTHORIZATION) String accessToken) {
        Long memberId = Long.valueOf(tokenProvider.getId(tokenProvider.resolveToken(accessToken)));
        try{
            KafkaOrderDTO orderDTO = orderService.orderPizza(memberId, requestPizzaOrderDTO);
            kafkaTemplate.send("kafka-test", orderDTO);
        }catch (DuplicateMemberException e) {
            return ResponseEntity.ok(new MsgDTO(false, "주문 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "주문 성공", null));
    }




}
