package org.spring.pizzarazzi.repository.pizza;

import org.spring.pizzarazzi.dto.pizza.DoughDTO;
import org.spring.pizzarazzi.dto.pizza.EdgeDTO;
import org.spring.pizzarazzi.model.pizza.Edge;
import org.spring.pizzarazzi.model.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EdgeRepository extends JpaRepository<Edge, Long> {
    boolean existsByName(String name);

    @Query("select new org.spring.pizzarazzi.dto.pizza.EdgeDTO(e.id, e.name, e.price) from Edge e")
    List<EdgeDTO> findAllEdgeDTO();

    @Query("select new org.spring.pizzarazzi.dto.pizza.EdgeDTO(e.id, e.name, e.price) from Edge e where e.id=:id")
    Optional<EdgeDTO> findByIdEdgeDTO(@Param("id") Long id);
}
