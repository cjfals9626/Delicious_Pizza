package org.spring.pizzarazzi.controller.pizza;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.common.MsgDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestAddEdgeDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestDeletePizzaComDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestUpdatePizzaComDTO;
import org.spring.pizzarazzi.exception.DuplicateEdgeException;
import org.spring.pizzarazzi.service.pizza.EdgeService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/edge")
public class EdgeController {
    private final EdgeService edgeService;

    @PostMapping("/add")
    public ResponseEntity<MsgDTO> addEdge(@RequestBody RequestAddEdgeDTO requestAddEdgeDTO) {
        try{
            edgeService.addEdge(requestAddEdgeDTO);
        }catch (DuplicateEdgeException e) {
            return ResponseEntity.ok(new MsgDTO(false, "엣지 추가 실패", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "엣지 추가 성공", null));
    }

    @GetMapping("/edges")
    public ResponseEntity<MsgDTO> getAllEdges() {
        return ResponseEntity.ok(new MsgDTO(true, "엣지 목록 조회 성공", edgeService.getAllEdges()));
    }

    @GetMapping("/edge/{id}")
    public ResponseEntity<MsgDTO> getEdgeById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(new MsgDTO(true, "엣지 조회 성공", edgeService.getEdgeById(id)));
        }catch (IncorrectResultSizeDataAccessException e) {
            return ResponseEntity.ok(new MsgDTO(false, "엣지 추가 실패", null));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<MsgDTO> deleteEdge(@RequestBody RequestDeletePizzaComDTO requestDeletePizzaComDTO) {
        try{
            edgeService.deleteEdge(requestDeletePizzaComDTO);
        }catch (EmptyResultDataAccessException e) {
            return ResponseEntity.ok(new MsgDTO(false, "엣지 삭제 실패. DB에 없는 데이터", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "엣지 삭제 성공", null));
    }

    @PostMapping("/update")
    public ResponseEntity<MsgDTO> updateEdge(@RequestBody RequestUpdatePizzaComDTO requestUpdatePizzaComDTO) {
        try{
            edgeService.updateEdge(requestUpdatePizzaComDTO);
        }catch (EmptyResultDataAccessException e) {
            return ResponseEntity.ok(new MsgDTO(false, "엣지 수정 실패. DB에 없는 데이터", null));
        }
        return ResponseEntity.ok(new MsgDTO(true, "엣지 수정 성공", null));
    }
}
