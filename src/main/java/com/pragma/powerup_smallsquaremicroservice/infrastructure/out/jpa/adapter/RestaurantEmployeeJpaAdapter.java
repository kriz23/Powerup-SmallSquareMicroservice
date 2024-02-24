package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter;

import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IRestaurantEmployeeEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IRestaurantEmployeeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantEmployeeJpaAdapter implements IRestaurantEmployeePersistencePort {
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;
    
    @Override
    public void assingEmployeeToRestaurant(Long idRestaurant, Long idEmployee) {
        restaurantEmployeeRepository
                .save(restaurantEmployeeEntityMapper.restaurantEmployeeToRestaurantEmployeeEntity(idRestaurant, idEmployee));
    }
}
