package org.spring.pizzarazzi.repository.pizza;

import org.spring.pizzarazzi.dto.pizza.DoughDTO;
import org.spring.pizzarazzi.model.pizza.Dough;
import org.spring.pizzarazzi.model.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoughRepository  extends JpaRepository<Dough, Long> {
    boolean existsByName(String name);

    @Query("select new org.spring.pizzarazzi.dto.pizza.DoughDTO(d.id, d.name, d.price) from Dough d")
    List<DoughDTO> findAllDoughDTO();

    @Query("select new org.spring.pizzarazzi.dto.pizza.DoughDTO(d.id, d.name, d.price) from Dough d")
    Optional<DoughDTO> findByIdDoughDTO(Long id);

}
