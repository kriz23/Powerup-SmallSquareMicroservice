package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity;

import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders") // Should have been named "orders" instead of "order" because "order" is a reserved word in SQL
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idClient;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    private OrderStateEnum state;
    private Long idChef;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurantEntity;
    private double amount;
    private String pin;
}
