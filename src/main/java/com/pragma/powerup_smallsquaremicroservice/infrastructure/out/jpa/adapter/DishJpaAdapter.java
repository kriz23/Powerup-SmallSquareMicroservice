package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter;

import com.pragma.powerup_smallsquaremicroservice.domain.exception.CategoryNotFoundException;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.GenericNameInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.DishAlreadyExistsException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final ICategoryRepository categoryRepository;
    
    
    @Override
    public void createDish(Dish dish) {
        if (dishRepository.findByName(dish.getName()).isPresent()) {
            throw new DishAlreadyExistsException();
        }
        dishRepository.save(dishEntityMapper.dishToDishEntity(dish));
    }
    
    @Override
    public Dish getDishById(Long idDish) {
        DishEntity dishEntity = dishRepository.findById(idDish).orElseThrow(NoDataFoundException::new);
        return dishEntityMapper.dishEntityToDish(dishEntity);
    }
    
    @Override
    public void updateDish(Dish dish) {
        dishRepository.save(dishEntityMapper.dishToDishEntity(dish));
    }
    
    @Override
    public boolean validateName(Dish dish) {
        if (dish.getName().isEmpty()) {
            throw new GenericNameInvalidException();
        }
        List<DishEntity> dishEntityList = dishRepository.findAllByRestaurantEntityId(dish.getRestaurant().getId());
        dishEntityList.forEach(dishEntity -> {
            if (dishEntity.getName().equals(dish.getName())) {
                throw new DishAlreadyExistsException();
            }
        });
        return true;
    }
    
    @Override
    public boolean validateCategoryExists(Long idCategory) {
        if (!categoryRepository.existsById(idCategory)) {
            throw new CategoryNotFoundException();
        }
        return true;
    }
}
