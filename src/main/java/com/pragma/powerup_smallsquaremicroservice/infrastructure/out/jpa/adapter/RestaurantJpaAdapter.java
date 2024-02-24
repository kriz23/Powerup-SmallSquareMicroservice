package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter;

import com.pragma.powerup_smallsquaremicroservice.domain.exception.RestaurantNotFoundException;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.RestaurantAlreadyExistsException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {
    
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    
    @Override
    public void createRestaurant(Restaurant restaurant) {
        if (restaurantRepository.findByNit(restaurant.getNit()).isPresent()) {
            throw new RestaurantAlreadyExistsException();
        }
        restaurantRepository.save(restaurantEntityMapper.restaurantToRestaurantEntity(restaurant));
    }
    
    @Override
    public Restaurant getRestaurantById(Long idRestaurant) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(idRestaurant).orElseThrow(
                NoDataFoundException::new);
        return restaurantEntityMapper.restaurantEntityToRestaurant(restaurantEntity);
    }
    
    @Override
    public boolean validateRestaurantExists(Long idRestaurant) {
        if (!restaurantRepository.existsById(idRestaurant)){
            throw new RestaurantNotFoundException();
        }
        return true;
    }
}
