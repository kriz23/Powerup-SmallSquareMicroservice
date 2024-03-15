package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository;

import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRestaurantEmployeeRepository extends JpaRepository<RestaurantEmployeeEntity, Long> {
    boolean existsByIdEmployee(Long idEmployee);
    RestaurantEmployeeEntity findByIdEmployee(Long idEmployee);
    List<RestaurantEmployeeEntity> findAllByRestaurantEntityId(Long idRestaurant);
}
