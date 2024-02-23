package org.spring.pizzarazzi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dough {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DoughId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;
}
