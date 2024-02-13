package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.DishAlreadyExistsException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    
    
    @Override
    public void createDish(Dish dish) {
        if (dishRepository.findByName(dish.getName()).isPresent()){
            throw new DishAlreadyExistsException();
        }
        dishRepository.save(dishEntityMapper.dishToDishEntity(dish));
    }
    
    @Override
    public Dish getDish(Long idDish) {
        DishEntity dishEntity = dishRepository.findById(idDish).orElseThrow(NoDataFoundException::new);
        return dishEntityMapper.dishEntityToDish(dishEntity);
    }
}
