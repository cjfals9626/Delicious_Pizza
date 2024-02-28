package org.spring.pizzarazzi.controller.pizza;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.common.MsgDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestAddToppingDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestDeletePizzaComDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestUpdatePizzaComDTO;
import org.spring.pizzarazzi.exception.DuplicateToppingException;
import org.spring.pizzarazzi.service.pizza.ToppingService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/topping")
public class ToppingController {
    private final ToppingService toppingService;

    @PostMapping("/add")
    public ResponseEntity<MsgDTO> addTopping(@RequestBody RequestAddToppingDTO requestAddToppingDTO) {
        try{
            toppingService.addTopping(requestAddToppingDTO);
        }catch (DuplicateToppingException e) {
            return ResponseEntity.ok(new MsgDTO(false, "토핑 추가 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "토핑 추가 성공", null));
    }

    @GetMapping("/toppings")
    public ResponseEntity<MsgDTO> getAllToppings() {
        return ResponseEntity.ok(new MsgDTO(true, "토핑 목록 조회 성공", toppingService.findAllToppings()));
    }

    @GetMapping("/topping/{id}")
    public ResponseEntity<MsgDTO> getToppingById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(new MsgDTO(true, "토핑 조회 성공", toppingService.findToppingById(id)));
        }catch (IncorrectResultSizeDataAccessException e) {
            return ResponseEntity.ok(new MsgDTO(false, "토핑 추가 실패", null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MsgDTO> deleteTopping(@RequestBody RequestDeletePizzaComDTO requestDeletePizzaComDTO) {
        try{
            toppingService.deleteTopping(requestDeletePizzaComDTO);
        }catch (EmptyResultDataAccessException e) {
            return ResponseEntity.ok(new MsgDTO(false, "토핑 삭제 실패. DB에 없는 데이터", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "토핑 삭제 성공", null));
    }

    @PutMapping("/update")
    public ResponseEntity<MsgDTO> updateTopping(@RequestBody RequestUpdatePizzaComDTO requestUpdatePizzaComDTO) {
        try{
            toppingService.updateTopping(requestUpdatePizzaComDTO);
        }catch (EmptyResultDataAccessException e) {
            return ResponseEntity.ok(new MsgDTO(false, "토핑 수정 실패. DB에 없는 데이터", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "토핑 수정 성공", null));
    }
}
