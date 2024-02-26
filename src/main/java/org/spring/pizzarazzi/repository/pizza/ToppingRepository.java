package org.spring.pizzarazzi.repository.pizza;

import org.spring.pizzarazzi.model.pizza.Topping;
import org.spring.pizzarazzi.model.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, Long> {
    boolean existsByName(String name);
}
