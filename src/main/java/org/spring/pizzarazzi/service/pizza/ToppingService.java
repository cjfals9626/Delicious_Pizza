package org.spring.pizzarazzi.service.pizza;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi.dto.pizza.ToppingDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestAddToppingDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestDeletePizzaComDTO;
import org.spring.pizzarazzi.dto.request.pizza.RequestUpdatePizzaComDTO;
import org.spring.pizzarazzi.model.pizza.Topping;
import org.spring.pizzarazzi.repository.pizza.ToppingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ToppingService {
    private final ToppingRepository toppingRepository;
    public void addTopping(RequestAddToppingDTO requestAddToppingDTO){
        Topping topping = requestAddToppingDTO.toTopping();

        toppingRepository.save(topping);
    }

    public List<ToppingDTO> findAllToppings(){
        return toppingRepository.findAllToppingDTO();
    }

    public ToppingDTO findToppingById(Long id) {
        return toppingRepository.findByIdToppingDTO(id)
                .orElseThrow(
                        () -> new NoSuchElementException("해당 토핑이 없습니다.")
                );
    }

    public void deleteTopping(RequestDeletePizzaComDTO requestDeletePizzaComDTO) {
        toppingRepository.deleteById(requestDeletePizzaComDTO.getId());
    }

    public void updateTopping(RequestUpdatePizzaComDTO requestUpdatePizzaComDTO) {
        Topping topping = toppingRepository.findById(requestUpdatePizzaComDTO.getId())
                        .orElseThrow(
                                () -> new NoSuchElementException("해당 토핑이 없습니다.")
                        );
        topping.setName(requestUpdatePizzaComDTO.getName());
        topping.setPrice(requestUpdatePizzaComDTO.getPrice());
    }
}
