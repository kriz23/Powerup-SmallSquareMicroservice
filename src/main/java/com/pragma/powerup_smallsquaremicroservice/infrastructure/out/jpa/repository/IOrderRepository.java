package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository;

import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByIdClientAndStateNotIn(Long idClient, List<OrderStateEnum> excludedStates);
    Page<OrderEntity> findByRestaurantEntityIdAndState(Long idRestaurant, OrderStateEnum state, Pageable pageable);
    List<OrderEntity> findByIdClientAndState(Long idClient, OrderStateEnum state);
}
