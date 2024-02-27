package org.spring.pizzarazzi.service.pizza;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.pizza.EdgeDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestAddEdgeDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestDeletePizzaComDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestUpdatePizzaComDTO;
import org.spring.pizzarazzi.model.pizza.Edge;
import org.spring.pizzarazzi.repository.pizza.EdgeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class EdgeService {
    private final EdgeRepository edgeRepository;

    public void addEdge(RequestAddEdgeDTO requestAddEdgeDTO){
        Edge edge = requestAddEdgeDTO.toEdge();

        edgeRepository.save(edge);
    }
    public List<EdgeDTO> getAllEdges(){
        return edgeRepository.findAllEdgeDTO();
    }

    public EdgeDTO getEdgeById(Long id) {
        return edgeRepository.findByIdEdgeDTO(id)
                .orElseThrow(
                        () -> new NoSuchElementException("해당 엣지가 없습니다.")
                );
    }

    public void deleteEdge(RequestDeletePizzaComDTO requestDeletePizzaComDTO) {
        edgeRepository.deleteById(requestDeletePizzaComDTO.getId());
    }

    public void updateEdge(RequestUpdatePizzaComDTO requestUpdatePizzaComDTO) {

        Edge edge = edgeRepository.findById(requestUpdatePizzaComDTO.getId())
                .orElseThrow(
                        () -> new NoSuchElementException("해당 엣지가 없습니다.")
                );
        edge.setName(requestUpdatePizzaComDTO.getName());
        edge.setPrice(requestUpdatePizzaComDTO.getPrice());
    }
}
