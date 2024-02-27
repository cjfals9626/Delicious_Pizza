package org.spring.pizzarazzi.repository.pizza;

import org.spring.pizzarazzi.dto.pizza.EdgeDTO;
import org.spring.pizzarazzi.dto.pizza.ToppingDTO;
import org.spring.pizzarazzi.model.pizza.Topping;
import org.spring.pizzarazzi.model.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, Long> {
    boolean existsByName(String name);

    @Query("select new org.spring.pizzarazzi.dto.pizza.ToppingDTO(t.id, t.name, t.price) from Topping t")
    List<ToppingDTO> findAllToppingDTO();

    @Query("select new org.spring.pizzarazzi.dto.pizza.ToppingDTO(t.id, t.name, t.price) from Topping t")
    Optional<ToppingDTO> findByIdToppingDTO(Long id);
}
