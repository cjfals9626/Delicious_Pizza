package org.spring.pizzarazzi.repository.pizza;

import org.spring.pizzarazzi.model.pizza.Edge;
import org.spring.pizzarazzi.model.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EdgeRepository extends JpaRepository<Edge, Long> {
    boolean existsByName(String name);
}
