package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository;

import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, Long> {
    List<OrderDishEntity> findAllByOrderEntityId(Long idOrder);
}
