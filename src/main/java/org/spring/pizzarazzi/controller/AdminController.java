package org.spring.pizzarazzi.controller;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.common.MsgDTO;
import org.spring.pizzarazzi.dto.kafka.KafkaOrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestPizzaOrderDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestTakeOrderDTO;
import org.spring.pizzarazzi.exception.DuplicateMemberException;
import org.spring.pizzarazzi.service.pizza.OrderService;
import org.spring.pizzarazzi.util.jwt.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import static org.spring.pizzarazzi.util.prefix.ConstPrefix.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final OrderService orderService;
    private final KafkaTemplate<String, KafkaOrderDTO> kafkaTemplate;

    @PutMapping("/take_order")
    public ResponseEntity<MsgDTO> takeOrder(@RequestBody RequestTakeOrderDTO requestTakeOrderDTO) {
        try{
            KafkaOrderDTO orderDTO = orderService.takeOrder(requestTakeOrderDTO);
            kafkaTemplate.send("kafka-test", orderDTO);
        }catch (DuplicateMemberException e) {
            return ResponseEntity.ok(new MsgDTO(false, "주문 접수 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "주문 접수 성공", null));
    }

    @PutMapping("/reject_order")
    public ResponseEntity<MsgDTO> rejectOrder(@RequestBody RequestTakeOrderDTO requestTakeOrderDTO) {
        try{
            KafkaOrderDTO orderDTO = orderService.rejectOrder(requestTakeOrderDTO);
            kafkaTemplate.send("kafka-test", orderDTO);
        }catch (DuplicateMemberException e) {
            return ResponseEntity.ok(new MsgDTO(false, "주문 거절 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "주문 거절 성공", null));
    }


    @PutMapping("/deliver_order")
    public ResponseEntity<MsgDTO> deliverOrder(@RequestBody RequestTakeOrderDTO requestTakeOrderDTO) {
        try{
            KafkaOrderDTO orderDTO = orderService.deliverOrder(requestTakeOrderDTO);
            kafkaTemplate.send("kafka-test", orderDTO);
        }catch (DuplicateMemberException e) {
            return ResponseEntity.ok(new MsgDTO(false, "주문 배달 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "주문 배달 성공", null));
    }


}
