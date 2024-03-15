package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter;

import com.pragma.powerup_smallsquaremicroservice.domain.model.RestaurantEmployee;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IRestaurantEmployeeEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IRestaurantEmployeeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RestaurantEmployeeJpaAdapter implements IRestaurantEmployeePersistencePort {
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;
    
    @Override
    public void assignEmployeeToRestaurant(Long idEmployee, Long idRestaurant) {
        restaurantEmployeeRepository
                .save(restaurantEmployeeEntityMapper.restaurantEmployeeToRestaurantEmployeeEntity(idEmployee, idRestaurant));
    }
    
    @Override
    public boolean validateEmployeeExists(Long idEmployee) {
        return restaurantEmployeeRepository.existsByIdEmployee(idEmployee);
    }
    
    @Override
    public Long getRestaurantId(Long idEmployee) {
        return restaurantEmployeeRepository.findByIdEmployee(idEmployee).getRestaurantEntity().getId();
    }
    
    @Override
    public List<RestaurantEmployee> getEmployeesByIdRestaurant(Long idRestaurant) {
        return restaurantEmployeeEntityMapper.restaurantEmployeeEntityListToRestaurantEmployeeList(
                restaurantEmployeeRepository.findAllByRestaurantEntityId(idRestaurant));
    }
}
