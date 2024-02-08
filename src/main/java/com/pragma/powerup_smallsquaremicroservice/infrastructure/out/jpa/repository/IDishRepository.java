package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository;

import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    Optional<DishEntity> findByName(String name);
    List<DishEntity> findAllByRestaurantEntityId(Long idRestaurant);
}
