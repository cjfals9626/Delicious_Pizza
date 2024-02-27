package org.spring.pizzarazzi.controller.pizza;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.common.MsgDTO;
import org.spring.pizzarazzi.dto.request.member.RequestMemberSignUpDTO;
import org.spring.pizzarazzi.dto.request.pizza.*;
import org.spring.pizzarazzi.exception.DuplicateDoughException;
import org.spring.pizzarazzi.exception.DuplicateEdgeException;
import org.spring.pizzarazzi.exception.DuplicateMemberException;
import org.spring.pizzarazzi.exception.DuplicateToppingException;
import org.spring.pizzarazzi.service.member.MemberService;
import org.spring.pizzarazzi.service.pizza.DoughService;
import org.spring.pizzarazzi.service.pizza.EdgeService;
import org.spring.pizzarazzi.service.pizza.PizzaService;
import org.spring.pizzarazzi.service.pizza.ToppingService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pizza")
public class PizzaController {
    private final MemberService memberService;
    private final PizzaService pizzaService;

    @PostMapping("/order")
    public ResponseEntity<MsgDTO> pizzaOrder(@RequestBody RequestPizzaOrderDTO requestPizzaOrderDTO) {
        try{
            pizzaService.orderPizza(requestPizzaOrderDTO);
        }catch (DuplicateMemberException e) {
            return ResponseEntity.ok(new MsgDTO(false, "주문 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "주문 성공", null));
    }



}
