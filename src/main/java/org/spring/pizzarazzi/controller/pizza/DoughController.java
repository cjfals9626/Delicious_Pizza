package org.spring.pizzarazzi.controller.pizza;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.common.MsgDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestAddDoughDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestDeletePizzaComDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestUpdatePizzaComDTO;
import org.spring.pizzarazzi.exception.DuplicateDoughException;
import org.spring.pizzarazzi.service.pizza.DoughService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dough")
public class DoughController {
    private final DoughService doughService;

    @PostMapping("/add")
    public ResponseEntity<MsgDTO> addDough(@RequestBody RequestAddDoughDTO requestAddDoughDTO) {
        try{
            doughService.addDough(requestAddDoughDTO);
        }catch (DuplicateDoughException e) {
            return ResponseEntity.ok(new MsgDTO(false, "도우 추가 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "도우 추가 성공", null));
    }

    @GetMapping("/doughs")
    public ResponseEntity<MsgDTO> getAllDoughs() {
        return ResponseEntity.ok(new MsgDTO(true, "도우 목록 조회 성공", doughService.getAllDoughs()));
    }

    @GetMapping("/dough/{id}")
    public ResponseEntity<MsgDTO> getDoughById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(new MsgDTO(true, "도우 조회 성공", doughService.getDoughById(id)));
        }catch (IncorrectResultSizeDataAccessException e) {
            return ResponseEntity.ok(new MsgDTO(false, "도우 추가 실패", null));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<MsgDTO> deleteDough(@RequestBody RequestDeletePizzaComDTO requestDeletePizzaComDTO) {
        try{
            doughService.deleteDough(requestDeletePizzaComDTO);
        }catch (EmptyResultDataAccessException e) {
            return ResponseEntity.ok(new MsgDTO(false, "도우 삭제 실패. DB에 없는 데이터", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "도우 삭제 성공", null));
    }

    @PostMapping("/update")
    public ResponseEntity<MsgDTO> updateDough(@RequestBody RequestUpdatePizzaComDTO requestUpdatePizzaComDTO) {
        try{
            doughService.updateDough(requestUpdatePizzaComDTO);
        }catch (EmptyResultDataAccessException e) {
            return ResponseEntity.ok(new MsgDTO(false, "도우 수정 실패. DB에 없는 데이터", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "도우 수정 성공", null));
    }

}
