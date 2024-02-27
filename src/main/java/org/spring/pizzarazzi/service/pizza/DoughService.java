package org.spring.pizzarazzi.service.pizza;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.pizza.DoughDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestAddDoughDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestDeletePizzaComDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestUpdatePizzaComDTO;
import org.spring.pizzarazzi.model.pizza.Dough;
import org.spring.pizzarazzi.repository.pizza.DoughRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class DoughService {
    private final DoughRepository doughRepository;
    public void addDough(RequestAddDoughDTO requestAddDoughDTO){
        Dough dough = requestAddDoughDTO.toDough();
        doughRepository.save(dough);
    }

    public List<DoughDTO> getAllDoughs(){
        return doughRepository.findAllDoughDTO();
    }

    public DoughDTO getDoughById(Long id) {
        return doughRepository.findByIdDoughDTO(id)
                .orElseThrow(
                        () -> new NoSuchElementException("해당 도우가 없습니다.")
                );
    }

    public void deleteDough(RequestDeletePizzaComDTO requestDeletePizzaComDTO) {
        doughRepository.deleteById(requestDeletePizzaComDTO.getId());
    }

    public void updateDough(RequestUpdatePizzaComDTO requestUpdatePizzaComDTO) {
        Dough dough = doughRepository.findById(requestUpdatePizzaComDTO.getId())
                        .orElseThrow(
                                () -> new NoSuchElementException("해당 도우가 없습니다.")
                        );
        dough.setName(requestUpdatePizzaComDTO.getName());
        dough.setPrice(requestUpdatePizzaComDTO.getPrice());
    }
}
