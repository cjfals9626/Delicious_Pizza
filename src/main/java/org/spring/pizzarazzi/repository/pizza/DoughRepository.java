package org.spring.pizzarazzi.repository.pizza;

import org.spring.pizzarazzi.model.pizza.Dough;
import org.spring.pizzarazzi.model.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoughRepository  extends JpaRepository<Dough, Long> {
    boolean existsByName(String name);

}
